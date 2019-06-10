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

package com.cristianmg.sqldelight.data

import com.cristianmg.sqldelight.data.service.CharacterService
import java.io.File
import java.lang.IllegalStateException
import kotlin.reflect.KClass


/**
 * Helper function which will load JSON from
 * the path specified
 *
 * @param path : Path of JSON file
 * @return json : JSON from file at given path
 */
fun getJson(relativePath: String, myClass: KClass<CharacterService>) : String {
    // Load the JSON response
    val path = "json/$relativePath"
    myClass.java.classLoader?.getResource(path)?.let {
        val file = File(it.path)
        return String(file.readBytes())
    }
    throw IllegalStateException("Resource not found")
}