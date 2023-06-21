package com.marcelo.marvelheroes.domain.usecases.interfaces

import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.HeroId
import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow

interface GetComicsEventsUseCase {
    operator fun invoke(params: HeroId): Flow<ResultStatus<List<DetailParentViewData>>>
}