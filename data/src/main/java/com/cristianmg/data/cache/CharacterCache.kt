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

package com.cristianmg.data.cache

import com.cristianmg.data.ext.DateFormat
import com.cristianmg.data.mapper.DCharacterMapper
import com.cristianmg.data.model.CharacterModel
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterCache {

    fun characters(): Single<List<CharacterModel>>
    fun insert(data: CharacterModel): Completable
    fun insertAll(list: List<CharacterModel>): Completable

    class Database(private val database: com.cristianmg.data.Database,
                   private val mapper:DCharacterMapper) : CharacterCache {

        override fun insertAll(list: List<CharacterModel>): Completable {
            return Completable.merge(list.map { insert(it) })
        }

        override fun insert(data: CharacterModel): Completable {
            return Completable.fromAction {
                database.characterQueries.insert(data.id,data.name,DateFormat.toDatabaseFormat(data.modified),data.resourceUri)
            }
        }

        override fun characters(): Single<List<CharacterModel>> {
            return Single.fromCallable {
                database.characterQueries.readAll().executeAsList()
            }.map {
                mapper.mapListToModel(it)
            }
        }
    }
}