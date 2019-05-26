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

import androidx.paging.DataSource
import com.cristianmg.sqldelight.data.cache.CharacterDao
import com.cristianmg.sqldelight.data.mapper.CharacterMapper
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.data.service.CharacterService
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterRepository {

    fun character(offset: Int,pageSize:Int): Single<List<CharacterModel>>
    fun insertAll(list: List<CharacterModel>): Completable
    fun getDataSourceCharacters(): DataSource.Factory<Int, CharacterModel>
    fun removeAll(): Completable

    open class CharacterRepositoryImpl(
        private val service: CharacterService,
        private val mapper: CharacterMapper,
        private val dao: CharacterDao
    ) : CharacterRepository {

        override fun removeAll(): Completable = dao.deleteAll()

        override fun getDataSourceCharacters(): DataSource.Factory<Int, CharacterModel> {
            return dao.getAllPaged()
                .map { mapper.mapToModel(it) }
        }

        override fun insertAll(list: List<CharacterModel>): Completable =
            dao.insertAll(mapper.mapListToEntity(list))

        override fun character(offset: Int,pageSize:Int): Single<List<CharacterModel>> =
            service.characters(offset, pageSize)
                .map {
                    mapper.mapListToModel(it)
                }

    }

}