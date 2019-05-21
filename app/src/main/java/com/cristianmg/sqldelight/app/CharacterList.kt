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

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianmg.sqldelight.R
import com.cristianmg.sqldelight.domain.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_character_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterList : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = CharacterAdapter {
            viewModel.retry()
        }
        rvCharacters.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rvCharacters.adapter = adapter

        viewModel.characterPages
            .observe(this, Observer {
                adapter.submitList(it)
            })


        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.updateCharacters()
    }

}
