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

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.cristianmg.sqldelight.domain.ext.GenericBoundaryCallback

/**
 * For a clean architecture
 * encapsulated all methods for paginate in this class
 *
 * For more information
 * @see https://github.com/googlesamples/android-architecture-components/blob/master/PagingWithNetworkSample
 **/
interface Listing<T> {

    fun getBoundaryCallback(): LiveData<GenericBoundaryCallback<T>>
    fun getDataSource(): LiveData<PagedList<CharacterModel>>
    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(getBoundaryCallback()) { it.networkState }
}