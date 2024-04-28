package com.marcelo.marvelheroes.domain.mapper

import com.marcelo.marvelheroes.data.local.entitys.HeroesEntity
import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.getHttpsUrl
import org.koin.core.annotation.Single

@Single
class DetailHeroesMapper {

    fun mapComicsResponseToDetailChildViewData(comicsResponse: ComicsResponse): List<DetailChildViewData> {
        val detailChildList = mutableListOf<DetailChildViewData>()

        val detailChild = DetailChildViewData(
            id = comicsResponse.id,
            imageUrl = comicsResponse.thumbnail.getHttpsUrl()
        )

        detailChildList.add(detailChild)

        return detailChildList
    }

    fun mapEventsResponseToDetailChildViewData(eventsResponse: EventsResponse): List<DetailChildViewData> {
        val detailChildList = mutableListOf<DetailChildViewData>()

        val detailChild = DetailChildViewData(
            id = eventsResponse.id,
            imageUrl = eventsResponse.thumbnail.getHttpsUrl()
        )

        detailChildList.add(detailChild)

        return detailChildList
    }

    fun mapHeroesEntityToHeroesViewData(heroesEntity: HeroesEntity) = HeroesViewData(
        id = heroesEntity.id,
        name = heroesEntity.name,
        imageUrl = heroesEntity.imageUrl
    )
}