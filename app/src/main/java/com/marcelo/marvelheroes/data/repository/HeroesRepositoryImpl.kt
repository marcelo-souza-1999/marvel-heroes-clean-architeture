package com.marcelo.marvelheroes.data.repository

import com.marcelo.marvelheroes.data.remote.datasource.HeroesPagingSource
import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.singleOrNull
import org.koin.core.annotation.Single

@Single
class HeroesRepositoryImpl(
    private val remoteDataSource: HeroesRemoteDataSource,
    private val detailHeroesMapper: DetailHeroesMapper
) : HeroesRepository {

    override fun getHeroes(query: String) = HeroesPagingSource(remoteDataSource, query)

    /*
    * In this function, we are retrieving a response containing a list of comics related to a specific hero.
    * The response object may have a data container, which contains a results list.
    * We use `flatMap` to map each item in the results list to a list of `ComicsViewData` using the `detailHeroesMapper`.
    * By using `flatMap`, the resulting lists are combined into a single list of `ComicsViewData`.
    * If the response or results list is null, an empty list is returned.
    */
    override suspend fun getComics(heroId: Int): List<DetailChildViewData> {
        val response = remoteDataSource.fetchComics(heroId).singleOrNull()
        return response?.dataContainerHeroes?.results?.flatMap { comicsResponse ->
            detailHeroesMapper.mapComicsResponseToDetailChildViewData(comicsResponse)
        } ?: emptyList()
    }

    /*
    * Similarly, in this function, we are retrieving a response containing a list of events related to a specific hero.
    * The response object may have a data container, which contains a results list.
    * We use `flatMap` to map each item in the results list to a list of `EventsViewData` using the `detailHeroesMapper`.
    * By using `flatMap`, the resulting lists are combined into a single list of `EventsViewData`.
    * If the response or results list is null, an empty list is returned.
    */
    override suspend fun getEvents(heroId: Int): List<DetailChildViewData> {
        val response = remoteDataSource.fetchEvents(heroId).singleOrNull()
        return response?.dataContainerHeroes?.results?.flatMap { eventsResponse ->
            detailHeroesMapper.mapEventsResponseToDetailChildViewData(eventsResponse)
        } ?: emptyList()
    }
}