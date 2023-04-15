package com.marcelo.marvelheroes.presentation.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutItemHeroesBinding
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.loadImage

class HeroesViewHolder(
    private val binding: LayoutItemHeroesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(heroesViewData: HeroesViewData) {
        binding.txtNameHeroes.text = heroesViewData.name
        binding.imageCharacter.loadImage(heroesViewData.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup): HeroesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutItemHeroesBinding.inflate(inflater, parent, false)
            return HeroesViewHolder(binding)
        }
    }
}
