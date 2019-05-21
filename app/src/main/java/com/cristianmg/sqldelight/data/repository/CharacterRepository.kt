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

package com.cristianmg.sqldelight.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.cristianmg.sqldelight.app.CharacterBoundayCallback
import com.cristianmg.sqldelight.data.cache.CharacterCache
import com.cristianmg.sqldelight.data.mapper.DCharacterMapper
import com.cristianmg.sqldelight.data.mapper.NCharacterMapper
import com.cristianmg.sqldelight.data.model.CharacterModel
import com.cristianmg.sqldelight.data.service.CharacterService
import com.cristianmg.sqldelight.domain.model.NetworkState
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.Executors

interface CharacterRepository {

    companion object {
        const val SIZE_PAGE = 5
    }

    fun listingCharacter(): Listing<CharacterModel>
    fun character(offset: Int): Single<List<CharacterModel>>
    fun insertAll(list: List<CharacterModel>): Completable

    open class CharacterRepositoryImpl(
        private val service: CharacterService,
        private val dMapper: DCharacterMapper,
        private val nMapper: NCharacterMapper,
        private val cache: CharacterCache
    ) : CharacterRepository {


        override fun listingCharacter(): Listing<CharacterModel> {
            val boundaryCallback = CharacterBoundayCallback(
                this,
                Executors.newSingleThreadExecutor(), SIZE_PAGE
            )

            // we are using a mutable live data to trigger refresh requests which eventually calls
            // refresh method and gets a new live data. Each refresh request by the user becomes a newly
            // dispatched data in refreshTrigger
            val refreshTrigger = MutableLiveData<Unit>()
            val refreshState = Transformations.switchMap(refreshTrigger) {
                refreshCharacter()
            }

            // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
            val livePagedList = cache.observeCharacters()
                .map { dMapper.mapToModel(it) }
                .toLiveData(
                    pageSize = SIZE_PAGE,
                    boundaryCallback = boundaryCallback
                )

            return Listing(
                pagedList = livePagedList,
                networkState = boundaryCallback.networkState,
                retry = {
                    boundaryCallback.helper.retryAllFailed()
                },
                refresh = {
                    refreshTrigger.value = null
                },
                refreshState = refreshState
            )

        }

        override fun insertAll(list: List<CharacterModel>): Completable =
            cache.insertAll(dMapper.mapListToEntity(list))

        override fun character(offset: Int): Single<List<CharacterModel>> =
            service.characters(offset, SIZE_PAGE)
                .map {
                    nMapper.mapListToModel(it)
                }


        @SuppressLint("CheckResult")
        fun refreshCharacter(): LiveData<NetworkState> {
            val networkState = MutableLiveData<NetworkState>()
            networkState.value = NetworkState.LOADING
            service.characters(0, SIZE_PAGE)
                .flatMapCompletable {
                    cache.deleteAllAndInsertNews(dMapper.mapListToEntity(nMapper.mapListToModel(it)))
                }
                .subscribeBy(
                    onComplete = {
                        networkState.postValue(NetworkState.LOADED)
                    },
                    onError = {
                        networkState.value = NetworkState.error(it.message)
                    }
                )
            return networkState
        }
    }

}