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

package com.cristianmg.sqldelight.domain.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import io.reactivex.Scheduler
import io.reactivex.Single
import com.cristianmg.sqldelight.domain.model.Result

fun <T> Single<T>.toLiveData() = toFlowable().toLiveData()

fun <T> Single<T>.toResult(scheduler: Scheduler): Single<Result<T>> =
    this.subscribeOn(scheduler)
        .map { Result.success(it) }
        .onErrorReturn { Result.failure(it) }

fun <T> Single<T>.toResultLiveData(scheduler: Scheduler): LiveData<Result<T>> = toResult(scheduler).toLiveData()
