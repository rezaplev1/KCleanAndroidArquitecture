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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cristianmg.sqldelight.domain.ext.toLiveData
import com.cristianmg.sqldelight.domain.ext.toResult
import com.cristianmg.sqldelight.domain.ext.toResultLiveData
import com.jraska.livedata.test
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import java.lang.IllegalStateException
import org.junit.Rule


class CompletableExtTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Test
    fun resultSuccessTest() {
        Completable.complete()
            .toResult(Schedulers.io())
            .test()
            .await()
            .assertComplete()
            .assertValue { it.isSuccess() }
    }

    @Test
    fun resultFailureTest() {
        Completable.error(IllegalStateException("resultFailureTest"))
            .toResult(Schedulers.io())
            .test()
            .await()
            .assertComplete()
            .assertValue { it.isFailure() }
    }


    @Test
    fun toLiveDataTestError() {
        Completable.error(IllegalStateException())
            .toResultLiveData(Schedulers.io())
            .test()
            .awaitValue()
            .assertValue { it.isFailure() }
    }


    @Test
    fun toLiveDataTestSuccess() {
        Completable.complete()
            .toResultLiveData(Schedulers.io())
            .test()
            .awaitValue()
            .assertValue { it.isSuccess() }
    }


}
