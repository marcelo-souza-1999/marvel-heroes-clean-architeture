package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.HeroId
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.usecase.BaseUseCase
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class GetComicsEventsEventsUseCaseImpl(
    private val repository: HeroesRepository,
    private val dispatcher: CoroutinesDispatchers
) : BaseUseCase<HeroId, List<DetailParentViewData>>(), GetComicsEventsUseCase {

    override suspend fun runUseCase(params: HeroId): ResultStatus<List<DetailParentViewData>> {
        return withContext(dispatcher.io()) {
            val comics = repository.getComics(params.heroId)
            val events = repository.getEvents(params.heroId)

            val detailParentList = mutableListOf<DetailParentViewData>()

            if (comics.isNotEmpty()) {
                detailParentList.add(
                    DetailParentViewData(
                        categories = COMICS,
                        detailChildList = comics
                    )
                )
            }

            if (events.isNotEmpty()) {
                detailParentList.add(
                    DetailParentViewData(
                        categories = EVENTS,
                        detailChildList = events
                    )
                )
            }

            ResultStatus.Success(data = detailParentList)
        }
    }

    companion object {
        @JvmInline
        value class HeroId(val heroId: Int)

        private const val COMICS = "Comics"
        private const val EVENTS = "Events"
    }
}