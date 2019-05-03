package com.cristianmg.data.cache

import com.cristianmg.data.ext.DateFormat
import com.cristianmg.data.mapper.DCharacterMapper
import com.cristianmg.data.model.CharacterModel
import io.reactivex.Completable
import io.reactivex.Single

interface CharacterCache {

    fun characters(): Single<List<CharacterModel>>
    fun insert(data: CharacterModel): Completable
    fun insertAll(list: List<CharacterModel>): Completable

    class Database(private val database: com.cristianmg.data.Database,
                   private val mapper:DCharacterMapper) : CharacterCache {

        override fun insertAll(list: List<CharacterModel>): Completable {
            return Completable.merge(list.map { insert(it) })
        }

        override fun insert(data: CharacterModel): Completable {
            return Completable.fromAction {
                database.characterQueries.insert(data.id,data.name,DateFormat.toDatabaseFormat(data.modified),data.resourceUri)
            }
        }

        override fun characters(): Single<List<CharacterModel>> {
            return Single.fromCallable {
                database.characterQueries.readAll().executeAsList()
            }.map {
                mapper.mapListToModel(it)
            }
        }
    }
}