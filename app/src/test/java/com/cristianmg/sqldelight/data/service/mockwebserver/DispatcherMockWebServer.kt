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

package com.cristianmg.sqldelight.data.service.mockwebserver

import com.cristianmg.sqldelight.data.getJson
import com.cristianmg.sqldelight.data.service.CharacterService
import com.google.gson.JsonParser
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class DispatcherMockWebServer : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestUrl = request.requestUrl

        return when (requestUrl.encodedPath()) {
            "/v1/public/characters" -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        getJson(
                            "characters/character_page.json",
                            CharacterService::class
                        )
                    )
            }
            else -> MockResponse().setResponseCode(404)
        }


    }

}