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
import androidx.lifecycle.toLiveData
import com.cristianmg.sqldelight.domain.ext.toLiveData
import com.cristianmg.sqldelight.domain.ext.toResult
import com.cristianmg.sqldelight.domain.ext.toResultLiveData
import com.jraska.livedata.test
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import java.lang.IllegalStateException
import org.junit.Rule
import java.util.concurrent.TimeUnit


class MaybeExtTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Test
    fun resultSuccessTest() {
        Maybe.just(1)
            .toResult(Schedulers.io())
            .test()
            .await()
            .assertComplete()
            .assertNever { !it.isSuccess() }
    }

    @Test
    fun resultSuccessEmptyTest() {
        Maybe.empty<Int>()
            .toResult(Schedulers.io())
            .test()
            .await()
            .assertComplete()
            .assertNoValues()
    }


    @Test
    fun resultFailureTest() {
        Maybe.error<Int>(IllegalStateException())
            .toResult(Schedulers.io())
            .test()
            .await()
            .assertComplete()
            .assertValue { it.isFailure() }
    }


    @Test
    fun toLiveDataTestError() {
        Maybe.error<Int>(IllegalStateException())
            .toResultLiveData(Schedulers.io())
            .test()
            .awaitValue()
            .assertValue { it.isFailure() }
    }

    @Test
    fun toLiveDataTestSuccess() {
        Maybe.just(1)
            .toResultLiveData(Schedulers.io())
            .test()
            .awaitValue()
            .assertNever { !it.isSuccess() }
    }

    @Test
    fun toLiveDataTestSuccessEmpty() {
        Maybe.empty<Int>()
            .toResultLiveData(Schedulers.io())
            .test()
            .awaitValue(1,TimeUnit.MILLISECONDS)
            .assertNoValue()
    }
}
