package com.marcelo.marvelheroes.presentation.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutItemNotConnectionBinding

class HeroesRefreshDataViewHolder(
    private val binding: LayoutItemNotConnectionBinding,
    private val retryLoad: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        with(binding) {
            progressLoadingData.isVisible = loadState is Loading
            txtTryAgainLoad.isVisible = loadState is Error
            txtTryAgainLoad.setOnClickListener {
                retryLoad()
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, retryLoad: () -> Unit): HeroesRefreshDataViewHolder {
            val itemBinding = LayoutItemNotConnectionBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return HeroesRefreshDataViewHolder(itemBinding, retryLoad)
        }
    }
}
