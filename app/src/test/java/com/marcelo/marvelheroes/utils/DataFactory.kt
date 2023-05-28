package com.marcelo.marvelheroes.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.model.HeroesPagingViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

val getHeroesFactory = HeroesViewData(
    name = "Scarlet Witch",
    imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/6/70/5261a7d7c394b.jpg",
    id = 1009562
)

val getEventsFactoryList = listOf(
    EventsViewData(
        id = 1009562,
        imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/40/51ca10d996b8b,jpg"
    )
)

val getEventsFactory = EventsViewData(
    id = 1009562,
    imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/40/51ca10d996b8b,jpg"
)

val getComicsFactoryList = listOf(
    ComicsViewData(
        id = 1009562,
        imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35,jpg"
    )
)

val getComicsFactory = ComicsViewData(
    id = 1009562,
    imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/00/64243b0cedf35,jpg"
)

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

val getHeroesPagingViewData = HeroesPagingViewData(
    offset = 0,
    total = 1,
    heroesList = listOf(getHeroesFactory)
)

const val ONE = 1
const val INVALID_URL = "invalid_url"
const val VALID_URL = "https://gateway.marvel.com/v1/public/"
const val TIMEOUT_SECONDS = 10L
const val THOUSAND = 1000
const val PAGING_SIZE = 20