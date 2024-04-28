package com.marcelo.marvelheroes.presentation.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.marcelo.marvelheroes.databinding.LayoutItemHeroesBinding
import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.presentation.adapters.utils.GenericViewHolder

class HeroesFavoriteViewHolder(
    private val binding: LayoutItemHeroesBinding,
) : GenericViewHolder<FavoriteItemData>(binding) {

    override fun bind(data: FavoriteItemData) = with(binding) {
        txtNameHeroes.text = data.name
        imageHeroe.transitionName = data.name
        imageHeroe.loadImage(data.imageUrl)
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): HeroesFavoriteViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutItemHeroesBinding.inflate(inflater, parent, false)
            return HeroesFavoriteViewHolder(binding)
        }
    }
}