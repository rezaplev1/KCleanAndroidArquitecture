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

package com.cristianmg.data.ext

import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest

fun String.toMd5(): String? {
    return try {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(this.toByteArray())
        val number = BigInteger(1, messageDigest)
        var md5 = number.toString(16)

        while (md5.length < 32)
            md5 = "0$md5"

        md5
    } catch (e: Exception) {
        null
    }
}