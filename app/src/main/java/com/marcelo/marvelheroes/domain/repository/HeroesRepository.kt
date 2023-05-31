package com.marcelo.marvelheroes.domain.repository

import androidx.paging.PagingSource
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

interface HeroesRepository {
    fun getHeroes(query: String): PagingSource<Int, HeroesViewData>

    suspend fun getComics(heroId: Int): List<DetailChildViewData>

    suspend fun getEvents(heroId: Int): List<DetailChildViewData>
}