package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.GetComicsParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.usecase.BaseUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class GetComicsEventsEventsUseCaseImpl(
    private val repository: HeroesRepository,
    private val dispatcher: CoroutinesDispatchers
) : BaseUseCase<GetComicsParams, Pair<List<ComicsViewData>, List<EventsViewData>>>(),
    GetComicsEventsUseCase {

    override suspend fun runUseCase(params: GetComicsParams): ResultStatus<Pair<List<ComicsViewData>, List<EventsViewData>>> {
        return withContext(dispatcher.io()) {
            val comicsAsync = async { repository.getComics(params.heroeId) }
            val eventsAsync = async { repository.getEvents(params.heroeId) }

            val comicsResult = comicsAsync.await()
            val eventsResult = eventsAsync.await()


            ResultStatus.Success(data = comicsResult to eventsResult)
        }
    }

    companion object {
        data class GetComicsParams(val heroeId: Int)
    }
}