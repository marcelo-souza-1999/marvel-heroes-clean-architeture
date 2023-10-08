package com.marcelo.marvelheroes.extensions

import com.marcelo.marvelheroes.data.local.entitys.FavoriteHeroEntity
import com.marcelo.marvelheroes.domain.model.HeroesViewData

fun List<FavoriteHeroEntity>.toHeroesViewData() = map {
    HeroesViewData(
        id = it.id,
        name = it.name,
        imageUrl = it.imageUrl
    )
}

fun HeroesViewData.toFavoriteHeroEntity() = FavoriteHeroEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)