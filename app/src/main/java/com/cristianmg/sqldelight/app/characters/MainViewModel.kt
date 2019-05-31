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

package com.cristianmg.sqldelight.app.characters


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.cristianmg.sqldelight.data.repository.CharacterRepository
import com.cristianmg.sqldelight.domain.ext.liveData
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.domain.model.Listing

class MainViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {


    private val listing: LiveData<Listing<CharacterModel>> by lazy {
        liveData(characterRepository.getListable())
    }

    private val boundaryCallback = switchMap(listing) { it.getBoundaryCallback() }!!
    val dataSource = switchMap(listing) { it.getDataSource() }!!
    val networkState = switchMap(listing) { it.getNetworkState() }!!


   /**
    * Retry all failed petitions boundary callback
    */
   fun retry() {
        boundaryCallback.value?.retryPetitions()
    }

    /**
     * Cleared all references and petitions boundary callback
     */
    override fun onCleared() {
        boundaryCallback.value?.cleared()
    }
}
