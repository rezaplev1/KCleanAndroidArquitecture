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
package com.cristianmg.domain.model
import androidx.lifecycle.Observer

/**
 * Following this article I realize that I could create a similar solution for reduce boilerplate code and reuse the observer
 * see this @https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 * **/
class ResultObserver<T>(
    private val success: (T) -> Unit,
    private val failure: (exception: Throwable?) -> Unit,
    private val oneShot: Boolean = false
) : Observer<Result<T>> {

    var hasBeenHandled = false
        private set

    override fun onChanged(t: Result<T>?) {
        if (t != null && (!oneShot || !hasBeenHandled)) {
            when {
                t.isSuccess -> success(t.getOrThrow())
                t.isFailure -> failure(t.exceptionOrNull())
            }
            hasBeenHandled = true
        }
    }
}