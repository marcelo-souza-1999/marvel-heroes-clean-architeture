package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class DeleteFavorite(
    private val repository: FavoritesHeroRepository
) {

    suspend operator fun invoke(
        heroId: Int
    ): Flow<Boolean> = flow {
        val isDelete = try {
            repository.deleteFavorite(
                idHero = heroId
            )
            true
        } catch (e: Exception) {
            false
        }

        emit(isDelete)
    }
}