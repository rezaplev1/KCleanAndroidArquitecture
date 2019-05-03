package com.cristianmg.data.service

import com.cristianmg.data.entity.MarvelPaginateData
import com.cristianmg.data.entity.MarvelResponseEntity
import com.cristianmg.data.entity.NCharacterEntity
import com.cristianmg.data.mapper.NCharacterMapper
import com.cristianmg.data.model.CharacterModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.http.Path


interface CharacterService {

    fun characters(): Single<List<CharacterModel>>

    class Network(
        private val mapper: NCharacterMapper
    ) : CharacterService {

        override fun characters(): Single<List<CharacterModel>> {

            val retrofit = Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NetworkCalls::class.java)
                .getCharacters(0, 10)
                .map { it.getDataOrError().results }
                .map {
                    mapper.mapListToModel(it)
                }

        }


        interface NetworkCalls {
            @GET("/v1/public/characters")
            fun getCharacters(@Path("offset") offset: Int, @Path("limit") limit: Int): Single<MarvelResponseEntity<MarvelPaginateData<List<NCharacterEntity>>>>
        }

    }
}