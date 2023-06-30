package com.marcelo.marvelheroes.domain.usecases.interfaces

import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCaseImpl.Companion.GetFavoritesParams
import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow

interface SaveFavoriteUseCase {

    operator fun invoke(params: GetFavoritesParams): Flow<ResultStatus<Unit>>
}