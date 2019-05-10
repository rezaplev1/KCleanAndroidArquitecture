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

package com.cristianmg.data.service

import com.cristianmg.data.entity.MarvelApiInformation
import com.cristianmg.data.entity.MarvelPaginateData
import com.cristianmg.data.entity.MarvelResponseEntity
import com.cristianmg.data.entity.NCharacterEntity
import com.cristianmg.data.mapper.NCharacterMapper
import com.cristianmg.data.model.CharacterModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.http.Query

interface CharacterService {

    fun characters(): Single<List<CharacterModel>>

    class Network(
        private val mapper: NCharacterMapper,
        private val apiInf: MarvelApiInformation,
        private val retrofit: Retrofit
    ) : CharacterService {

        override fun characters(): Single<List<CharacterModel>> {
            val ts = apiInf.ts
            return retrofit.create(NetworkCalls::class.java)
                .getCharacters(ts, apiInf.publicApiKey, apiInf.getHash(ts), 0, 1)
                .map { it.getDataOrError().results }
                .map {
                    mapper.mapListToModel(it)
                }
        }


        interface NetworkCalls {

            @GET("/v1/public/characters")
            fun getCharacters(
                @Query("ts") ts: String,
                @Query("apikey") apiKey: String,
                @Query("hash") hash: String?, @Query("offset") offset: Int,
                @Query("limit") limit: Int
            ): Single<MarvelResponseEntity<MarvelPaginateData<List<NCharacterEntity>>>>

        }

    }


}