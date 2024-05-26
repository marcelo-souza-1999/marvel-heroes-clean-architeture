package com.marcelo.marvelheroes.presentation.adapters.heroes

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.marcelo.marvelheroes.presentation.adapters.viewholder.HeroesRefreshDataViewHolder

class HeroesRefreshDataAdapter(
    private val retryLoad: () -> Unit
) : LoadStateAdapter<HeroesRefreshDataViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = HeroesRefreshDataViewHolder.create(parent, retryLoad)

    override fun onBindViewHolder(
        holder: HeroesRefreshDataViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}