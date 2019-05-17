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

package com.cristianmg.sqldelight.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.cristianmg.sqldelight.domain.viewmodel.MainViewModel
import com.cristianmg.sqldelight.R
import com.cristianmg.sqldelight.domain.model.ResultObserver
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val resultFailure = Result.failure<String>(IllegalStateException())
        val resultSucces = Result.success("")



        viewModel.characters().observe(this, Observer {
            Timber.d("")
        })

        //viewModel.characters().observe(this,ResultObserver{})

       /* ResultObserver(
            {
                Timber.d("List received")
            },{

            }*/

    }
}
