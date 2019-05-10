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

package com.cristianmg.domain

abstract class CRUseCase<T> : UnsubscribeListener {

    private var onNext: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onComplete: (() -> Unit)? = null
    private var subscriber: DisposableSubscriber<T>? = null
    var isInProgress = false
        get() { return subscriber != null }


    fun execute(onNext: ((T) -> Unit)?  = null, onError: ((Throwable) -> Unit)?  = null, onComplete: (() -> Unit)? = null) {

        if(subscriber != null){
            unsubscribe() //Unsubscribe previous subscription
        }

        this.onNext = onNext
        this.onError = onError
        this.onComplete = onComplete

        Flowable.defer { buildUseCase() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { it.printStackTrace() }
                .subscribe(getSubscriber())

    }


    abstract fun buildUseCase(): Flowable<T>

    fun getSubscriber(): DisposableSubscriber<T> {

        if (subscriber != null && subscriber!!.isDisposed) {
            subscriber = null
        }

        if (subscriber == null) {
            subscriber = object : DisposableSubscriber<T>() {
                override fun onComplete() {
                    onComplete?.invoke()
                }

                override fun onError(e: Throwable) {
                    onError?.invoke(e)

                }

                override fun onNext(t: T) {
                    onNext?.invoke(t)
                }
            }
        }

        return subscriber!!
    }

    fun unsubscribe() {
        try {
            subscriber?.dispose()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        subscriber = null

    }


    override fun onUnsubscribe() {
        unsubscribe()
    }
}
