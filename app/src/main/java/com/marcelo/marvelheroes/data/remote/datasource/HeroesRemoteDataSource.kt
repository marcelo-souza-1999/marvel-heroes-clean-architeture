package com.marcelo.marvelheroes.data.remote.datasource

import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import kotlinx.coroutines.flow.Flow

interface HeroesRemoteDataSource {

    fun fetchHeroes(queries: Map<String, String>): Flow<DataContainerResponse<HeroesResponse>>

    fun fetchComics(heroId: Int): Flow<DataContainerResponse<ComicsResponse>>

    fun fetchEvents(heroId: Int): Flow<DataContainerResponse<EventsResponse>>

    /*suspend fun fetchHeroes(queries: Map<String, String>): DataContainerResponse<HeroesResponse>

    suspend fun fetchComics(heroId: Int): DataContainerResponse<ComicsResponse>

    suspend fun fetchEvents(heroId: Int): DataContainerResponse<EventsResponse>*/
}