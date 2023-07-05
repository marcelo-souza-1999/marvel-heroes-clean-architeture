package com.marcelo.marvelheroes.data.local.repository

import com.marcelo.marvelheroes.domain.datasource.FavoritesHeroLocalDataSource
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import org.koin.core.annotation.Single

@Single
class FavoritesHeroRepositoryImpl(
    private val localDataSource: FavoritesHeroLocalDataSource
) : FavoritesHeroRepository {

    override suspend fun getAllFavorites() = localDataSource.getAll()

    override suspend fun saveFavorite(heroes: HeroesViewData) = localDataSource.save(heroes)

    override suspend fun deleteFavorite(idHero: Int) = localDataSource.delete(idHero)
}