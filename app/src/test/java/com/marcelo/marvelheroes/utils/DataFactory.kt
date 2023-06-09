package com.marcelo.marvelheroes.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.DataContainerHeroesResponse
import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import com.marcelo.marvelheroes.data.remote.model.ThumbnailResponse
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.emptyString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val heroIdDefault = 1009562
private const val imageUrlDefault = "https://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35.jpg"
private const val heroNameDefault = "Scarlet Witch"

val getHeroesFactory = HeroesViewData(
    name = heroNameDefault,
    imageUrl = imageUrlDefault,
    id = heroIdDefault
)

val getEventsFactory = EventsViewData(
    id = heroIdDefault,
    imageUrl = imageUrlDefault
)

val getComicsFactory = ComicsViewData(
    id = heroIdDefault,
    imageUrl = imageUrlDefault
)

val getHeroesPagingSourceFactory = HeroesViewData(
    name = heroNameDefault,
    imageUrl = imageUrlDefault,
    id = heroIdDefault
)

val getDetailChildFactoryList = listOf(
    DetailChildViewData(
        id = heroIdDefault,
        imageUrl = imageUrlDefault
    )
)

val getComicsResponseList = listOf(
    ComicsResponse(
        id = heroIdDefault,
        thumbnail = ThumbnailResponse(
            path = "https://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35",
            extension = "jpg"
        )
    )
)

val getEventsResponseList = listOf(
    EventsResponse(
        id = heroIdDefault,
        thumbnail = ThumbnailResponse(
            path = "https://i.annihil.us/u/prod/marvel/i/mg/9/40/51ca10d996b8b",
            extension = "jpg"
        )
    )
)

val getHeroesFactoryFlow: Flow<DataContainerResponse<HeroesResponse>> = flow {
    emit(
        value = DataContainerResponse(
            copyright = emptyString(),
            dataContainerHeroes = DataContainerHeroesResponse(
                offset = ZERO,
                total = TOTAL_PAGING_SOURCE,
                results = listOf(
                    HeroesResponse(
                        id = heroIdDefault,
                        name = heroNameDefault,
                        thumbnail = ThumbnailResponse(
                            path = "http://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35",
                            extension = "jpg"
                        )
                    )
                )
            )
        )
    )
}

val getComicsFactoryFlow: Flow<DataContainerResponse<ComicsResponse>> = flow {
    emit(
        value = DataContainerResponse(
            copyright = emptyString(),
            dataContainerHeroes = DataContainerHeroesResponse(
                offset = ZERO,
                total = TOTAL_PAGING_SOURCE,
                results = listOf(
                    ComicsResponse(
                        id = heroIdDefault,
                        thumbnail = ThumbnailResponse(
                            path = "https://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35",
                            extension = "jpg"
                        )
                    )
                )
            )
        )
    )
}

val getEventsFactoryFlow: Flow<DataContainerResponse<EventsResponse>> = flow {
    emit(
        value = DataContainerResponse(
            copyright = emptyString(),
            dataContainerHeroes = DataContainerHeroesResponse(
                offset = ZERO,
                total = TOTAL_PAGING_SOURCE,
                results = listOf(
                    EventsResponse(
                        id = heroIdDefault,
                        thumbnail = ThumbnailResponse(
                            path = "https://i.annihil.us/u/prod/marvel/i/mg/9/40/51ca10d996b8b",
                            extension = "jpg"
                        )
                    )
                )
            )
        )
    )
}

val getPagingSourceFactory = object : PagingSource<Int, HeroesViewData>() {
    override fun getRefreshKey(state: PagingState<Int, HeroesViewData>) = ONE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroesViewData> {
        return LoadResult.Page(
            data = listOf(getHeroesFactory),
            prevKey = null,
            nextKey = PAGING_SIZE
        )
    }
}

const val ZERO = 0
const val ONE = 1
const val TOTAL_PAGING_SOURCE = 1562
const val INVALID_URL = "invalid_url"
const val VALID_URL = "https://gateway.marvel.com/v1/public/"
const val TIMEOUT_SECONDS = 10L
const val THOUSAND = 1000
const val PAGING_SIZE = 20
