package com.cristianmg.data.repository

import com.cristianmg.data.cache.CharacterCache
import com.cristianmg.data.model.CharacterModel
import com.cristianmg.data.service.CharacterService
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterRepository {

    fun characters(): Single<List<CharacterModel>>
    fun insertAll(list: List<CharacterModel>): Completable

    class CharacterRepositoryImpl(
        private val service: CharacterService,
        private val cache: CharacterCache
    ) : CharacterRepository {
        override fun insertAll(list: List<CharacterModel>): Completable = cache.insertAll(list)
        override fun characters(): Single<List<CharacterModel>> = service.characters()
    }
}