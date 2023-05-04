package com.marcelo.marvelheroes.data.repository.interfaces

import androidx.paging.PagingSource
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.HeroesViewData

interface HeroesRepository {
    fun getHeroes(query: String): PagingSource<Int, HeroesViewData>

    suspend fun getComics(heroeId: Int): List<ComicsViewData>
}