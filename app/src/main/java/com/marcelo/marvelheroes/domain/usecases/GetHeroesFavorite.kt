package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.mapper.HeroesFavoriteMapper
import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class GetHeroesFavorite(
    private val repository: FavoritesHeroRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteItemData>> = flow {
        repository.getAllFavorites().collect { favoriteHeroes ->
            emit(
                favoriteHeroes.map {
                    HeroesFavoriteMapper.mapHeroesToFavoriteItem(it)
                }
            )
        }
    }
}