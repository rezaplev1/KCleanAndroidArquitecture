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

package com.cristianmg.sqldelight.data.mockwebserver

import com.cristianmg.sqldelight.data.getJson
import com.cristianmg.sqldelight.data.service.CharacterService
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber
import java.io.IOException
import java.lang.Exception


class MockWebServerRule : TestRule {

    companion object {
        const val MOCK_WEBSERVER_PORT = 8000
        const val MOCK_WEB_SERVER_URL = "http://localhost:$MOCK_WEBSERVER_PORT"
    }

    private lateinit var mServer: MockWebServer

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                startServer()
                try {
                    base?.evaluate()
                } finally {
                    stopServer()
                }
            }
        }
    }


    fun server(): MockWebServer {
        return mServer
    }

    fun startServer() {
        mServer = MockWebServer()
        try {
            mServer.start(MOCK_WEBSERVER_PORT)

            mServer.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    val path = request.requestUrl.encodedPath()
                    return when (path) {
                        "/v1/public/characters" ->
                            MockResponse()
                                .setResponseCode(200)
                                .setBody(
                                    getJson(
                                        "characters/character_page.json",
                                        CharacterService::class
                                    )
                                )
                        else -> MockResponse().setResponseCode(404)
                    }

                }
            }


        } catch (e: Exception) {
            Timber.e(e, "mock server start issue")
        }

    }

    fun stopServer() {
        try {
            mServer.shutdown();
        } catch (e: IOException) {
            Timber.e(e, "mock server shutdown error")
        }
    }


}