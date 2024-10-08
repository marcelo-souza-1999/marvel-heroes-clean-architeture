package com.marcelo.marvelheroes.presentation.adapters.heroes

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.presentation.adapters.viewholder.HeroesViewHolder
import com.marcelo.marvelheroes.utils.alias.OnHeroItemClick

class HeroesAdapter(
    private val onItemClick: OnHeroItemClick
) : PagingDataAdapter<HeroesViewData, HeroesViewHolder>(adapter_callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HeroesViewHolder.create(parent, onItemClick)

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {

        private val adapter_callback = object : DiffUtil.ItemCallback<HeroesViewData>() {
            override fun areItemsTheSame(
                oldItem: HeroesViewData,
                newItem: HeroesViewData
            ) = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: HeroesViewData,
                newItem: HeroesViewData
            ) = oldItem == newItem
        }
    }
}