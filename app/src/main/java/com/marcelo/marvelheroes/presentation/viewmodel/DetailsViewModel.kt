package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.model.EventsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.GetComicsParams
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

    fun getComics(heroeId: Int) = viewModelScope.launch {
        getComicsEventsUseCase(GetComicsParams(heroeId))
            .observeStatus()
    }

    private fun Flow<ResultStatus<Pair<List<ComicsViewData>, List<EventsViewData>>>>.observeStatus() =
        onEach { status ->
            _viewState.value = when (status) {
                is Loading -> DetailsViewModelState.Loading
                is Error -> DetailsViewModelState.Error
                is Success -> {
                    val detailParentList = mutableListOf<DetailParentViewData>()

                    status.data.first.map { comics ->
                        DetailChildViewData(comics.id, comics.imageUrl)
                    }.takeIf { it.isNotEmpty() }?.let {
                        detailParentList.add(
                            DetailParentViewData(
                                categoriesResId = R.string.details_comics_category,
                                detailChildList = it
                            )
                        )
                    }

                    status.data.second.map { events ->
                        DetailChildViewData(events.id, events.imageUrl)
                    }.takeIf { it.isNotEmpty() }?.let {
                        detailParentList.add(
                            DetailParentViewData(
                                categoriesResId = R.string.details_events_category,
                                detailChildList = it
                            )
                        )
                    }

                    DetailsViewModelState.Success(detailParentList)
                }
            }
        }.launchIn(viewModelScope)

    sealed class DetailsViewModelState {
        object Loading : DetailsViewModelState()
        data class Success(val data: List<DetailParentViewData>) :
            DetailsViewModelState()

        object Error : DetailsViewModelState()
    }
}