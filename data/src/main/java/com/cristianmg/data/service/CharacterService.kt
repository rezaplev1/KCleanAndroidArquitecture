package com.cristianmg.data.service

import android.util.Base64
import com.cristianmg.data.entity.MarvelPaginateData
import com.cristianmg.data.entity.MarvelResponseEntity
import com.cristianmg.data.entity.NCharacterEntity
import com.cristianmg.data.mapper.NCharacterMapper
import com.cristianmg.data.model.CharacterModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.util.Log
import com.cristianmg.data.BuildConfig


interface CharacterService {

    fun characters(): Single<List<CharacterModel>>

    class Network(
        private val mapper: NCharacterMapper
    ) : CharacterService {

        override fun characters(): Single<List<CharacterModel>> {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASEPATH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            val privateApiKey = BuildConfig.PRIVATE_API_KEY_MARVEL
            val publicApiKey = BuildConfig.PUBLIC_API_KEY_MARVEL
            val ts = System.currentTimeMillis().toString()
            val hash = getMd5Hash(ts + privateApiKey + publicApiKey)!!

            return retrofit.create(NetworkCalls::class.java)
                .getCharacters(ts, publicApiKey, hash, 0, 1)
                .map { it.getDataOrError().results }
                .map {
                    mapper.mapListToModel(it)
                }

        }

        fun getMd5Hash(input: String): String? {
            try {
                val md = MessageDigest.getInstance("MD5")
                val messageDigest = md.digest(input.toByteArray())
                val number = BigInteger(1, messageDigest)
                var md5 = number.toString(16)

                while (md5.length < 32)
                    md5 = "0$md5"

                return md5
            } catch (e: NoSuchAlgorithmException) {
                Log.e("MD5", e.localizedMessage)
                return null
            }

        }

        interface NetworkCalls {
            @GET("/v1/public/characters")
            fun getCharacters(
                @Query("ts") ts: String,
                @Query("apikey") apiKey: String,
                @Query("hash") hash: String, @Query("offset") offset: Int,
                @Query("limit") limit: Int
            ):
                    Single<MarvelResponseEntity<MarvelPaginateData<List<NCharacterEntity>>>>
        }

    }


}