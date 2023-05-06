package com.marcelo.marvelheroes.extensions

import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

private const val HTTP = "http"
private const val HTTPS = "https"

fun HeroesResponse.toHeroesViewData() = HeroesViewData(
    id = id,
    name = name,
    imageUrl = "${thumbnail.path}.${thumbnail.extension}".replace(HTTP, HTTPS)
)

fun ComicsResponse.toComicsViewData() = ComicsViewData(
    id = id,
    imageUrl = "${thumbnail.path}.${thumbnail.extension}".replace(HTTP, HTTPS)
)