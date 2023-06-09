package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl.Companion.HeroId
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
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

    private val _viewState = MutableStateFlow(DetailsViewState(isLoading = true))
    val viewState = _viewState.asStateFlow()


    fun getHeroesDetails(heroId: Int) = viewModelScope.launch {
        getComicsEventsUseCase(HeroId(heroId))
            .onEach(::handleSuccess)
            .launchIn(viewModelScope)
    }

    private fun handleSuccess(resultStatus: ResultStatus<List<DetailParentViewData>>) {
        if (resultStatus is Error) _viewState.value = DetailsViewState(error = true)

        if (resultStatus is Success) {
            _viewState.value =
                if (resultStatus.data.isNotEmpty()) DetailsViewState(success = resultStatus.data)
                else DetailsViewState(empty = true)
        }
    }

    companion object {
        data class DetailsViewState(
            val isLoading: Boolean = false,
            val empty: Boolean = false,
            val error: Boolean = false,
            val success: List<DetailParentViewData> = emptyList()
        )
    }
}