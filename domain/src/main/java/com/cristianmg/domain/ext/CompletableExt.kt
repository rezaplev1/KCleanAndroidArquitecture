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

package com.cristianmg.domain.ext

import androidx.lifecycle.toLiveData
import com.cristianmg.domain.model.Event
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

fun <T> Completable.toLiveData() = toSingleDefault(Event()).toFlowable().toLiveData()

fun Completable.toResult(scheduler: Scheduler): Single<Result<Event>> {
    return this.subscribeOn(scheduler)
        .andThen(Single.fromCallable { return@fromCallable Result.success(Event()) })
        .onErrorReturn { e ->
            Result.failure(e)
        }
}

fun Completable.toResultLiveData(scheduler: Scheduler) = toResult(scheduler).toLiveData()

