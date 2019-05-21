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

package com.cristianmg.sqldelight.app

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.cristianmg.sqldelight.data.model.CharacterModel
import com.cristianmg.sqldelight.data.repository.CharacterRepository
import com.cristianmg.sqldelight.domain.ext.createStatusLiveData
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.Executor

class CharacterBoundayCallback(
    private val repository: CharacterRepository,
    private val ioExecutor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<CharacterModel>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var offsetCount = 0


    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            getTop(offsetCount,it)
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


    fun getTop(offset: Int, it: PagingRequestHelper.Request.Callback) {
        compositeDisposable.add(repository.character(offset)
            .flatMapCompletable {
                insertItemsIntoDb(it)
            }
            .subscribeBy(onComplete = {
                it.recordSuccess()
                offsetCount += networkPageSize
            }, onError = { throwable ->
                it.recordFailure(throwable)
            })
        )
    }

    /**
     * Insert items into db
     * **/
    private fun insertItemsIntoDb(
        list: List<CharacterModel>
    ): Completable {
        return repository.insertAll(list)
    }

}
