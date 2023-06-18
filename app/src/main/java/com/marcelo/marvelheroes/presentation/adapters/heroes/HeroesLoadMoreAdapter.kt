package com.marcelo.marvelheroes.presentation.adapters.heroes

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.marcelo.marvelheroes.presentation.adapters.viewholder.HeroesLoadMoreViewHolder

class HeroesLoadMoreAdapter(
    private val retryLoad: () -> Unit
) : LoadStateAdapter<HeroesLoadMoreViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = HeroesLoadMoreViewHolder.create(parent, retryLoad)

    override fun onBindViewHolder(
        holder: HeroesLoadMoreViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}