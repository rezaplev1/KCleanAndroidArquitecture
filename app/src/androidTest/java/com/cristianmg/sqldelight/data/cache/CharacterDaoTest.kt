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

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cristianmg.sqldelight.data.entity.CharacterEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest : KoinTest {

    val dao: CharacterDao by inject()

    @Before
    fun before() {
        //CharacterEntity("id","",)
        //loadKoinModules(cacheModule)
    }


    @Test
    fun testTest() {
        //CharacterEntity.cre
        dao.characters().test().assertComplete()
    }

}