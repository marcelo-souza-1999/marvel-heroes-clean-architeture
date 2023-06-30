package com.marcelo.marvelheroes.domain.datasource

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import kotlinx.coroutines.flow.Flow

interface FavoritesHeroLocalDataSource {

    suspend fun getAll(): List<HeroesViewData>

    suspend fun save(heroes: HeroesViewData)

    suspend fun delete(heroes: HeroesViewData)
}