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

package com.cristianmg.sqldelight

import android.app.Application
import com.cristianmg.sqldelight.app.di.AppKoinModules
import com.cristianmg.sqldelight.data.di.cacheModule
import com.cristianmg.sqldelight.data.di.mapperModule
import com.cristianmg.sqldelight.data.di.repositoryModule
import com.cristianmg.sqldelight.data.di.serviceModule
import com.cristianmg.sqldelight.domain.di.useCaseModule
import com.cristianmg.sqldelight.domain.di.viewModelModule
import com.orhanobut.logger.Logger
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import com.orhanobut.logger.AndroidLogAdapter



class App : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidLogger()
            androidContext(this@App)
            modules(viewModelModule, repositoryModule, useCaseModule, cacheModule, mapperModule, serviceModule)
        }

        RxJavaPlugins.setErrorHandler {
            Timber.e("The error not be delivered to callback ${it.message} ")
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })

            Logger.addLogAdapter(object : AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })

        }

    }
}