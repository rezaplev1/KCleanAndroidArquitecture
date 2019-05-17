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

package com.cristianmg.sqldelight.domain.model

sealed class Result<T> {


    fun getOrThrow(): T {
        if (this is Success<T> && data != null) {
            return data
        } else {
            throw IllegalStateException("Operation is fail ----  cannot be get value from failure")
        }
    }

    /**
     * return value or null
     * */
    fun getOrNull(): T? {
        return if (this is Success<T> && data != null)
            data
        else
            null
    }

    fun getExceptionOrNull(): Throwable? {
        if (this is Failure)
            return e
        return null
    }

    fun isFailure(): Boolean = this is Failure
    fun isSuccess(): Boolean = this is Success


    data class Success<T>(var data: T) : Result<T>()
    data class Failure<T>(val e: Throwable) : Result<T>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> failure(e: Throwable): Result<T> = Failure(e)
    }
}