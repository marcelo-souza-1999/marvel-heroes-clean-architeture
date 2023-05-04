package com.marcelo.marvelheroes.data.repository.interfaces

import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.HeroesPagingViewData

interface HeroesRemoteDataSource {
    suspend fun fetchHeroes(queries: Map<String, String>): HeroesPagingViewData

    suspend fun fetchComics(heroId: Int): List<ComicsViewData>
}