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

package com.cristianmg.sqldelight.data.repository

import androidx.paging.toLiveData
import androidx.room.Transaction
import com.cristianmg.sqldelight.data.cache.CharacterDao
import com.cristianmg.sqldelight.data.mapper.CharacterMapper
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.data.service.CharacterService
import com.cristianmg.sqldelight.domain.ext.GenericBoundaryCallback
import com.cristianmg.sqldelight.domain.ext.liveData
import com.cristianmg.sqldelight.domain.model.Listing
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Character repository, for a clean architecture only methods
 * that might be share between cache and service must be exposed by repository
 * **/
interface CharacterRepository {

    fun getListable(): Listing<CharacterModel>

    open class CharacterRepositoryImpl(
        private val service: CharacterService,
        private val mapper: CharacterMapper,
        private val dao: CharacterDao
    ) : CharacterRepository {

        companion object {
            const val SIZE_PAGE = 30
        }

        override fun getListable(): Listing<CharacterModel> {
            return object : Listing<CharacterModel> {

                /** Create the boundary callback **/
                val bc: GenericBoundaryCallback<CharacterModel> by lazy {
                    GenericBoundaryCallback(
                        { dao.deleteAll() },
                        { character(it, SIZE_PAGE) },
                        { insertCharacters(it) },
                        SIZE_PAGE
                    )
                }

                override fun getDataSource() =
                    dao.getAllPaged()
                        .map { mapper.mapToModel(it) }
                        .toLiveData(
                            pageSize = SIZE_PAGE,
                            boundaryCallback = bc
                        )


                override fun getBoundaryCallback() = liveData(bc)

            }
        }

        fun insertCharacters(list:List<CharacterModel>):Completable{
           return dao.insertAll(list.map { mapper.mapToEntity(it) })
        }

        fun character(offset: Int, pageSize: Int): Single<List<CharacterModel>> =
            service.characters(offset, pageSize)
                .map {
                    mapper.mapListToModel(it)
                }

    }

}