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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cristianmg.sqldelight.domain.ext.toResultLiveData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalStateException

class ResultObserverTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()


    /**
     * Check if Result observer respects items emmited when not oneshot flag
     * **/
    @Test
    fun observerSuccessTest() {

        var historyCount = 0

        Flowable.just(1, 2, 3)
            .toResultLiveData(Schedulers.trampoline())
            .observeForever(
                ResultObserver({
                    Assert.assertTrue("History count not is correct ----- $historyCount", historyCount <= 2)
                    historyCount++
                }, {
                    Assert.fail("Unexpected error")
                }, false)
            )
    }

    /**
     * Check if Result observer with flag onshot only emmit one item
     * **/
    @Test
    fun observerSuccessWithOneShotFlagTest() {
        var historyCount = 0
        Flowable.just(1, 2, 3,4,5,6)
            .toResultLiveData(Schedulers.trampoline())
            .observeForever(
                ResultObserver({
                    Assert.assertTrue("History count not is correct ----- $historyCount", historyCount <= 1)
                    historyCount++
                }, {
                    Assert.fail("Unexpected error")
                }, true)
            )
    }

    @Test
    fun observerErrorWithoutOneShotFlagTest() {
        Flowable.error<Int>(IllegalStateException("Test exception"))
            .toResultLiveData(Schedulers.trampoline())
            .observeForever(
                ResultObserver({
                    Assert.fail("Unexpected received value")
                }, {
                    Assert.assertTrue(it is IllegalStateException)
                }, false)
            )
    }
}
