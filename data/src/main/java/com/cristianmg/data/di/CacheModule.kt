package com.cristianmg.data.di

import com.cristianmg.data.Database
import com.cristianmg.data.cache.CharacterCache
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

val cacheModule = module {
    single {
        Database(AndroidSqliteDriver(Database.Schema, get(), "marvel.db"))
    }
    single<CharacterCache> { CharacterCache.Database(get()) }
}