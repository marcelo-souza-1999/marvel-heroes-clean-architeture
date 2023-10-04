package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single

@Single
class DeleteFavoriteUseCase(
    private val repository: FavoritesHeroRepository,
    private val dispatcher: CoroutinesDispatchers
) {

    suspend operator fun invoke(
        heroId: Int
    ): Flow<ResultStatus<Unit>> = flow {
        try {
            repository.deleteFavorite(
                idHero = heroId
            )
            emit(ResultStatus.Success(data = Unit))
        } catch (e: Exception) {
            emit(ResultStatus.Error(e))
        }
    }.flowOn(dispatcher.io())
}