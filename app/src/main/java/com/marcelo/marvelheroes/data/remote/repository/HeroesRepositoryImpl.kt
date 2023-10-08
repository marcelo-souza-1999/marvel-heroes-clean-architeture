package com.marcelo.marvelheroes.data.remote.repository

import com.marcelo.marvelheroes.data.remote.datasource.HeroesPagingSource
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
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

    override suspend fun getComics(heroId: Int): List<DetailChildViewData> {
        val response = remoteDataSource.fetchComics(heroId).singleOrNull()
        return response?.dataContainerHeroes?.results?.flatMap { comicsResponse ->
            detailHeroesMapper.mapComicsResponseToDetailChildViewData(comicsResponse)
        } ?: emptyList()
    }

    override suspend fun getEvents(heroId: Int): List<DetailChildViewData> {
        val response = remoteDataSource.fetchEvents(heroId).singleOrNull()
        return response?.dataContainerHeroes?.results?.flatMap { eventsResponse ->
            detailHeroesMapper.mapEventsResponseToDetailChildViewData(eventsResponse)
        } ?: emptyList()
    }
}