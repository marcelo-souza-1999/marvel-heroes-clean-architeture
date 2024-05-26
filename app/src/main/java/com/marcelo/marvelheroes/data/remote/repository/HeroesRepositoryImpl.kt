package com.marcelo.marvelheroes.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteMediator
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import org.koin.core.annotation.Single

@OptIn(ExperimentalPagingApi::class)
@Single
class HeroesRepositoryImpl(
    private val remoteDataSource: HeroesRemoteDataSource,
    private val database: MarvelDatabase,
    private val detailHeroesMapper: DetailHeroesMapper
) : HeroesRepository {

    override fun getCachedHeroes(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<HeroesViewData>> {
        val pagingSourceFactory = { database.createHeroDao().pagingSource() }

        return Pager(
            config = pagingConfig,
            remoteMediator = HeroesRemoteMediator(query, orderBy, database, remoteDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map(detailHeroesMapper::mapHeroesEntityToHeroesViewData)
        }
    }

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