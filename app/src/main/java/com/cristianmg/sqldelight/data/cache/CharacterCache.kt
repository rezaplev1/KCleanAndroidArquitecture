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

package com.cristianmg.sqldelight.data.cache

import androidx.paging.DataSource
import com.cristianmg.data.DCharacterEntity
import com.squareup.sqldelight.android.paging.QueryDataSourceFactory
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterCache {


    fun characters(): Single<List<DCharacterEntity>>
    fun insert(data: DCharacterEntity): Completable
    fun insertAll(list: List<DCharacterEntity>): Completable
    fun observeCharacters(): DataSource.Factory<Int, DCharacterEntity>
    fun deleteAllAndInsertNews(it: List<DCharacterEntity>): Completable

    class Database(
        private val database: com.cristianmg.sqldelight.Database
    ) : CharacterCache {

        override fun deleteAllAndInsertNews(it: List<DCharacterEntity>): Completable {
            return Completable.fromAction {
                database.transaction {
                    database.characterQueries.deleteAll()
                    it.forEach {
                        insertItem(it)
                    }
                }
            }
        }

        override fun observeCharacters(): QueryDataSourceFactory<DCharacterEntity> {
            return QueryDataSourceFactory(
                queryProvider = database.characterQueries::getByPage,
                countQuery = database.characterQueries.countCharacters()
            )
        }

        override fun insertAll(list: List<DCharacterEntity>): Completable {
            return Completable.fromAction {
                database.transaction {
                    list.forEach {
                        insertItem(it)
                    }
                }
            }
        }

        override fun insert(data: DCharacterEntity): Completable {
            return Completable.fromAction {
                insertItem(data)
            }
        }

        override fun characters(): Single<List<DCharacterEntity>> {
            return Single.fromCallable {
                database.characterQueries.readAll().executeAsList()
            }
        }


        private fun insertItem(data: DCharacterEntity){
            database.characterQueries.insert(
                data.id,
                data.name,
                data.modified,
                data.resourceURI
            )
        }
    }
}