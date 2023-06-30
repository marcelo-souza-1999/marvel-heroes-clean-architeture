package com.marcelo.marvelheroes.domain.repository

import com.marcelo.marvelheroes.domain.model.HeroesViewData
import kotlinx.coroutines.flow.Flow

interface FavoritesHeroRepository {

    suspend fun getAllFavorites(): List<HeroesViewData>

    suspend fun saveFavorite(heroes: HeroesViewData)

    suspend fun deleteFavorite(heroes: HeroesViewData)
}