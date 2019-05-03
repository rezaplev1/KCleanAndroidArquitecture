package com.cristianmg.sqldelight

import android.app.Application
import com.cristianmg.data.di.DataKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(){


    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidLogger()
            androidContext(this@App)
            // declare modules

            modules(DataKoinModules.getModules())
        }

    }
}