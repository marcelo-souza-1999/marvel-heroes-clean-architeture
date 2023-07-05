package com.marcelo.marvelheroes.domain.datasource

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import kotlinx.coroutines.flow.Flow

interface FavoritesHeroLocalDataSource {

    fun getAll(): Flow<List<HeroesViewData>>

    suspend fun save(heroes: HeroesViewData)

    suspend fun delete(idHero: Int)
}