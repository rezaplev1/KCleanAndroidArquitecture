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


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cristianmg.sqldelight.data.service.mockwebserver.MockWebServerRule
import com.cristianmg.sqldelight.di.TestKoinModules
import org.junit.Rule
import org.junit.Test
import org.koin.test.inject
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest


/**
 * This test are for testing de CharacterService
 * **/
class CharacterServiceTest : AutoCloseKoinTest() {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @get:Rule
    var mockWebServer = MockWebServerRule()

    private val service: CharacterService by inject()

    @Before
    fun before() {
        startKoin {
            modules(TestKoinModules.getModules())
        }
    }

    @Test
    fun charactersTest() {
        service.characters(0, 1)
            .test()
            .await()
            .assertValue { it.size == 1 }
    }


}
