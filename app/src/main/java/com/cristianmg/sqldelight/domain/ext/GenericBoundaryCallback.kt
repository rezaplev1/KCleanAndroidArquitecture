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

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.domain.model.NetworkState
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executors

class GenericBoundaryCallback<T>(
    private val removeAllItems: () -> Completable,
    private val getPage: (page: Int) -> Single<List<T>>,
    private val insertAllItems: (items: List<T>) -> Completable,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<CharacterModel>() {

    private val helper = PagingRequestHelper(Executors.newSingleThreadExecutor())
    val networkState: MutableLiveData<NetworkState> = helper.createStatusLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var offsetCount = 0


    fun refreshPage() {
        networkState.value = NetworkState.LOADING
        compositeDisposable.add(
            getPage(0)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    removeAllItems()
                        .andThen(insertAllItems(it))
                }.subscribeBy(
                    onComplete = {
                        networkState.postValue(NetworkState.LOADED)
                    },
                    onError = {
                        networkState.value = NetworkState.error(it.message)
                    }
                ))
    }

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            getTop(offsetCount, it)
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: CharacterModel) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            getTop(offsetCount, it)
        }
    }


    override fun onItemAtFrontLoaded(itemAtFront: CharacterModel) {
        // ignored, since we only ever append to what's in the DB
    }


    private fun getTop(offset: Int, pagingRequest: PagingRequestHelper.Request.Callback) {
        Timber.d("Request a new page $offset")
        val throwable = getPage(offset)
            .flatMapCompletable {
                insertAllItems(it)
            }.blockingGet()

        if (throwable != null) {
            Timber.e("Error when getTop with page $offset and message error ${throwable.message}")
            networkState.postValue(NetworkState.error(throwable.message))
            pagingRequest.recordFailure(throwable)
        } else {
            pagingRequest.recordSuccess()
            offsetCount += networkPageSize
        }

    }


    /** Clear all references **/
    fun cleared() {
        compositeDisposable.clear()
    }

    fun retryPetitions()  = helper.retryAllFailed()


}
