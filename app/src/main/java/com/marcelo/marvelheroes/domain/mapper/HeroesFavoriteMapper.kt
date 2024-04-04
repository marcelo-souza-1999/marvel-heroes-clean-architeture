package com.marcelo.marvelheroes.domain.mapper

import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

object HeroesFavoriteMapper {
    fun mapHeroesToFavoriteItem(hero: HeroesViewData): FavoriteItemData {
        return FavoriteItemData(
            id = hero.id,
            name = hero.name,
            imageUrl = hero.imageUrl
        )
    }
}