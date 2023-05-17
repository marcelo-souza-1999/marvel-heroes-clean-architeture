package com.marcelo.marvelheroes.extensions

import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import com.marcelo.marvelheroes.data.remote.model.ThumbnailResponse
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

private const val HTTP = "http"
private const val HTTPS = "https"

fun HeroesResponse.toHeroesViewData() = HeroesViewData(
    id = id,
    name = name,
    imageUrl = thumbnail.getHttpsUrl()
)

fun ComicsResponse.toComicsViewData() = ComicsViewData(
    id = id,
    imageUrl = thumbnail.getHttpsUrl()
)

fun EventsResponse.toEventsViewData() = EventsViewData(
    id = id,
    imageUrl = thumbnail.getHttpsUrl()
)

fun ThumbnailResponse.getHttpsUrl() = "${path}.${extension}".replace(HTTP, HTTPS)