package com.cristianmg.data.di

import com.cristianmg.data.service.CharacterService
import org.koin.dsl.module

val serviceModule = module {
    single<CharacterService> { CharacterService.Network() }
}