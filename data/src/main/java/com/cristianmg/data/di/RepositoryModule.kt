package com.cristianmg.data.di

import com.cristianmg.data.repository.CharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepository.CharacterRepositoryImpl(get(), get()) }
}