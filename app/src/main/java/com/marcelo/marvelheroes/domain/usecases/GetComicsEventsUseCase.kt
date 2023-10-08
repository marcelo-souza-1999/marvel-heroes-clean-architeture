package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.factories.DetailParentFactory
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class GetComicsEventsUseCase(
    private val repository: HeroesRepository,
    private val detailParentFactory: DetailParentFactory
) {

    suspend operator fun invoke(heroId: Int): Flow<State<List<DetailParentViewData>>> {
        val comics = repository.getComics(heroId)
        val events = repository.getEvents(heroId)

        val detailParentList = mutableListOf<DetailParentViewData>()
        if (comics.isNotEmpty()) {
            detailParentList.add(detailParentFactory.createDetailParent("Comics", comics))
        }

        if (events.isNotEmpty()) {
            detailParentList.add(detailParentFactory.createDetailParent("Events", events))
        }

        return flow {
            emit(State.Success(data = detailParentList))
        }
    }
}