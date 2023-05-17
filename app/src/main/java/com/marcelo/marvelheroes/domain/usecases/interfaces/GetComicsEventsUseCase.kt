package com.marcelo.marvelheroes.domain.usecases.interfaces

import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.GetComicsParams
import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow

interface GetComicsEventsUseCase {
    operator fun invoke(params: GetComicsParams): Flow<ResultStatus<Pair<List<ComicsViewData>, List<EventsViewData>>>>
}