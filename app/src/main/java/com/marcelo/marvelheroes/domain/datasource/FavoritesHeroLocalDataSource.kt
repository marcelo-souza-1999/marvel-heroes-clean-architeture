package com.marcelo.marvelheroes.domain.datasource

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import kotlinx.coroutines.flow.Flow

interface FavoritesHeroLocalDataSource {

    suspend fun getAll(): Flow<List<HeroesViewData>>

    suspend fun isFavorite(idHero: Int): Boolean

    suspend fun save(heroes: HeroesViewData)

    suspend fun delete(idHero: Int)
}