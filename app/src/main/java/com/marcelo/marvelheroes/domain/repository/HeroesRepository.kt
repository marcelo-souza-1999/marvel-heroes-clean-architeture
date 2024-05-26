package com.marcelo.marvelheroes.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import kotlinx.coroutines.flow.Flow

interface HeroesRepository {

    fun getCachedHeroes(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<HeroesViewData>>

    suspend fun getComics(heroId: Int): List<DetailChildViewData>

    suspend fun getEvents(heroId: Int): List<DetailChildViewData>
}