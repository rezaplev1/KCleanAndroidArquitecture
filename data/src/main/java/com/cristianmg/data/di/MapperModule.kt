package com.cristianmg.data.di

import com.cristianmg.data.mapper.DCharacterMapper
import com.cristianmg.data.mapper.NCharacterMapper
import org.koin.dsl.module

val mapperModule = module {
    single { DCharacterMapper() }
    single { NCharacterMapper() }
}