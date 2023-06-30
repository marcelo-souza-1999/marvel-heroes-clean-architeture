package com.marcelo.marvelheroes.data.local.datasource

import com.marcelo.marvelheroes.data.local.dao.FavoriteHeroDao
import com.marcelo.marvelheroes.domain.datasource.FavoritesHeroLocalDataSource
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.toFavoriteHeroEntity
import com.marcelo.marvelheroes.extensions.toHeroesViewData
import org.koin.core.annotation.Single

@Single
class FavoritesHeroLocalDataSourceImpl(
    private val favoriteHeroDao: FavoriteHeroDao
) : FavoritesHeroLocalDataSource {

    override suspend fun getAll() = favoriteHeroDao.getFavorites().toHeroesViewData()

    override suspend fun save(heroes: HeroesViewData) = favoriteHeroDao.insertFavorite(
        heroes.toFavoriteHeroEntity()
    )

    override suspend fun delete(heroes: HeroesViewData) = favoriteHeroDao.deleteFavorite(
        heroes.toFavoriteHeroEntity()
    )
}