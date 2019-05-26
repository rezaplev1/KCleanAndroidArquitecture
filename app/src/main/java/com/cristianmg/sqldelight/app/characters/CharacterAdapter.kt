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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cristianmg.sqldelight.R
import com.cristianmg.sqldelight.BR
import com.cristianmg.sqldelight.domain.model.CharacterModel
import com.cristianmg.sqldelight.domain.model.NetworkState

class CharacterAdapter(
    private val retryCallback: () -> Unit
) : PagedListAdapter<CharacterModel, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.character_item -> (holder as CharacterViewHolder).bindTo(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.character_item -> CharacterViewHolder.create(
                parent,
                retryCallback
            )
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(
                parent,
                retryCallback
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.character_item
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun create(parent: ViewGroup, retryCallback: () -> Unit): CharacterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.character_item, parent, false)
                return CharacterViewHolder(view)
            }
        }

        private val binding: ViewDataBinding? = DataBindingUtil.bind(view)

        fun bindTo(character: CharacterModel?) {
            binding?.setVariable(BR.character, character)
        }
    }


    class NetworkStateItemViewHolder(view: View, val retryCallback: () -> Unit)
        : RecyclerView.ViewHolder(view) {
        private val binding: ViewDataBinding? = DataBindingUtil.bind(view)

        fun bindTo(networkState: NetworkState?) {
            binding?.setVariable(BR.networkState, networkState)
            binding?.setVariable(BR.retryListener, View.OnClickListener {
                retryCallback()
            })
        }

        companion object {
            fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.network_state_item, parent, false)
                return NetworkStateItemViewHolder(
                    view,
                    retryCallback
                )
            }
        }

    }

    companion object {

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CharacterModel>() {
            override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
                oldItem.id == newItem.id

        }

    }
}
