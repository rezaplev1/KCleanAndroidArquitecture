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
import androidx.room.*
import com.cristianmg.sqldelight.data.entity.CharacterEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CharacterDao {

    @Query(
        """
            SELECT * FROM character
            """)
    fun characters(): Single<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: CharacterEntity): Completable

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CharacterEntity>): Completable

    @Query("""
            SELECT * FROM character ORDER BY name ASC
            """)
    fun getAllPaged(): DataSource.Factory<Int, CharacterEntity>

    @Query("""
        DELETE FROM character
    """)
    fun deleteAll(): Completable

}