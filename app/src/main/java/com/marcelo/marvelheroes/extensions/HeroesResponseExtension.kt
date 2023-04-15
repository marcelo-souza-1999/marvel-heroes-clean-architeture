package com.marcelo.marvelheroes.extensions

import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import com.marcelo.marvelheroes.domain.model.HeroesViewData

private const val HTTP = "http"
private const val HTTPS = "https"
fun HeroesResponse.toHeroesViewData() = HeroesViewData(
    name = name,
    imageUrl = "${thumbnail.path}.${thumbnail.extension}".replace(HTTP, HTTPS)
)