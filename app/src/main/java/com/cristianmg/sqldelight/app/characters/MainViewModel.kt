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


import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.cristianmg.sqldelight.domain.ext.GenericBoundaryCallback
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.data.repository.CharacterRepository


class MainViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    companion object {
        const val SIZE_PAGE = 20
    }

    /**
     * Make generic boundary callback
     */
    private val boundaryCallback: GenericBoundaryCallback<CharacterModel> by lazy {
        GenericBoundaryCallback(
            { characterRepository.removeAll() },
            { characterRepository.character(it, SIZE_PAGE) },
            { characterRepository.insertAll(it) },
            SIZE_PAGE
        )
    }


    val networkState = boundaryCallback.networkState

    /**
     * Make data source create Character data source
     */
    fun characters() = characterRepository.getDataSourceCharacters()
        .toLiveData(
            pageSize = SIZE_PAGE,
            boundaryCallback = boundaryCallback
        )

    //Inform to boundary callback of user press retry option
    fun retry() =
        boundaryCallback.retryPetitions()


    override fun onCleared() =
        boundaryCallback.cleared()

}