package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.GetComicsEventsParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Loading
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    private val getComicsEventsUseCase: GetComicsEventsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailsViewModelState>(DetailsViewModelState.Loading)
    val viewState = _viewState.asStateFlow()

    fun getHeroesDetails(heroeId: Int) = viewModelScope.launch {
        getComicsEventsUseCase
            .invoke(GetComicsEventsParams(heroeId))
            .observeStatus()
    }

    private fun Flow<ResultStatus<Pair<List<ComicsViewData>, List<EventsViewData>>>>.observeStatus() =
        onEach { status ->
            val detailParentList = mutableListOf<DetailParentViewData>()

            when (status) {
                is Loading -> _viewState.value = DetailsViewModelState.Loading
                is Error -> _viewState.value = DetailsViewModelState.Error
                is Success -> {

                    val comicsList = status.data.first.map { comics ->
                        DetailChildViewData(comics.id, comics.imageUrl)
                    }.takeIf { it.isNotEmpty() }

                    val eventsList = status.data.second.map { events ->
                        DetailChildViewData(events.id, events.imageUrl)
                    }.takeIf { it.isNotEmpty() }

                    comicsList?.let {
                        detailParentList.add(
                            DetailParentViewData(
                                categories = COMICS,
                                detailChildList = it
                            )
                        )
                    }

                    eventsList?.let {
                        detailParentList.add(
                            DetailParentViewData(
                                categories = EVENTS,
                                detailChildList = it
                            )
                        )
                    }

                    if (detailParentList.isNotEmpty()) _viewState.value =
                        DetailsViewModelState.Success(detailParentList)
                    else _viewState.value = DetailsViewModelState.Empty
                }
            }
        }.launchIn(viewModelScope)


    companion object {
        sealed class DetailsViewModelState {
            object Loading : DetailsViewModelState()
            object Empty : DetailsViewModelState()
            data class Success(val data: List<DetailParentViewData>) :
                DetailsViewModelState()

            object Error : DetailsViewModelState()
        }

        private const val COMICS = "Comics"
        private const val EVENTS = "Events"
    }
}