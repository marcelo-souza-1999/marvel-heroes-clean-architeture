package com.marcelo.marvelheroes.presentation.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutItemHeroesBinding
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.utils.alias.OnHeroeItemClick

class HeroesViewHolder(
    private val binding: LayoutItemHeroesBinding,
    private val onItemClick: OnHeroeItemClick
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(heroesViewData: HeroesViewData) = with(binding) {
        txtNameHeroes.text = heroesViewData.name
        imageHeroe.transitionName = heroesViewData.name
        imageHeroe.loadImage(heroesViewData.imageUrl)

        itemView.setOnClickListener { onItemClick.invoke(heroesViewData, imageHeroe) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick: OnHeroeItemClick
        ): HeroesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutItemHeroesBinding.inflate(inflater, parent, false)
            return HeroesViewHolder(binding, onItemClick)
        }
    }
}
