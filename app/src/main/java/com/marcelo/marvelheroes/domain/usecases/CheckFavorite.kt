package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class CheckFavorite(
    private val repository: FavoritesHeroRepository
) {

    suspend operator fun invoke(heroId: Int): Flow<Boolean> = flow {
        val isFavorite =
            runCatching { repository.isFavorite(heroId) }.getOrDefault(defaultValue = false)
        emit(isFavorite)
    }
}