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

package com.cristianmg.sqldelight.domain.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.cristianmg.sqldelight.data.repository.CharacterRepository


class MainViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {


    private val update = MutableLiveData<String>()

    private val repoResult =
        map(update) {
            characterRepository.listingCharacter()
        }

    val characterPages = Transformations.switchMap(repoResult) { it.pagedList }!!
    val networkState = Transformations.switchMap(repoResult) { it.networkState }!!
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }!!


    fun characters() = characterRepository.listingCharacter()

    fun retry() {

    }

    fun updateCharacters() {
        update.value = ""
    }


}