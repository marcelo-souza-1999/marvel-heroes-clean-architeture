package com.marcelo.marvelheroes.domain.usecases.interfaces

import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsUseCaseImpl.Companion.GetComicsParams
import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow

interface GetComicsUseCase {
    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<List<ComicsViewData>>>
}