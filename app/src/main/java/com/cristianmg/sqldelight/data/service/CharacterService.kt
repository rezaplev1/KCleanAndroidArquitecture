/*
 * Copyright 2019 Cristian Menárguez González
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cristianmg.sqldelight.data.service

import com.cristianmg.sqldelight.data.entity.MarvelApiInformationEntity
import com.cristianmg.sqldelight.data.entity.MarvelPaginateDataEntity
import com.cristianmg.sqldelight.data.entity.MarvelResponseEntity
import com.cristianmg.sqldelight.data.entity.CharacterEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.http.Query

interface CharacterService {

    fun characters(offset: Int, sizePage: Int): Single<List<CharacterEntity>>

    class Network(
        private val apiInf: MarvelApiInformationEntity,
        private val retrofit: Retrofit
    ) : CharacterService {

        override fun characters(offset: Int, sizePage: Int): Single<List<CharacterEntity>> {
            val ts = apiInf.ts
            return retrofit.create(NetworkCalls::class.java)
                .getCharacters(ts, apiInf.publicApiKey, apiInf.getHash(ts), offset, sizePage,"modified")
                .subscribeOn(Schedulers.io())
                .map { it.getDataOrError().results }
        }


        interface NetworkCalls {

            @GET("/v1/public/characters")
            fun getCharacters(
                @Query("ts") ts: String,
                @Query("apikey") apiKey: String,
                @Query("hash") hash: String?, @Query("offset") offset: Int,
                @Query("limit") limit: Int,@Query("orderBy") orderBy:String
            ): Single<MarvelResponseEntity<MarvelPaginateDataEntity<List<CharacterEntity>>>>

        }

    }


}