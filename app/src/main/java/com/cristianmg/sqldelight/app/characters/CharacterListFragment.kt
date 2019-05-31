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

package com.cristianmg.sqldelight.app.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianmg.sqldelight.R
import kotlinx.android.synthetic.main.fragment_character_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class CharacterListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
    }
    private val adapter: CharacterAdapter by lazy { CharacterAdapter { viewModel.retry() } }

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
        rvCharacters.layoutManager = layoutManager
        rvCharacters.adapter = adapter
        rvCharacters.setHasFixedSize(true)

        viewModel.dataSource
            .observe(this, Observer {
                adapter.submitList(it)
            })

        viewModel.networkState
            .observe(this, Observer {
                Timber.d("Received a new NetworkState STATUS ---- ${it.status} Message  ----- ${it.msg}")
                adapter.setNetworkState(it)
            })

         layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
              override fun getSpanSize(position: Int): Int {
                  return if ((position == adapter.itemCount - 1) && adapter.hasExtraRow()) 3 else 1
              }
          }


    }

}
