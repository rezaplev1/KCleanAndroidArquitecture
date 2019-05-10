/*
 * Copyright 2019 Jake Wharton
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

package com.cristianmg.data.repository

import com.cristianmg.data.cache.CharacterCache
import com.cristianmg.data.model.CharacterModel
import com.cristianmg.data.service.CharacterService
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterRepository {

    fun characters(): Single<List<CharacterModel>>
    fun insertAll(list: List<CharacterModel>): Completable

    class CharacterRepositoryImpl(
        private val service: CharacterService,
        private val cache: CharacterCache
    ) : CharacterRepository {
        override fun insertAll(list: List<CharacterModel>): Completable = cache.insertAll(list)
        override fun characters(): Single<List<CharacterModel>> = service.characters()
    }
}