package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.ComicsViewData
import com.marcelo.marvelheroes.domain.usecases.GetComicsUseCaseImpl.Companion.GetComicsParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsUseCase
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Loading
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailsViewModelState>(DetailsViewModelState.Loading)
    val viewState = _viewState.asStateFlow()

    fun getComics(heroeId: Int) = viewModelScope.launch {
        getComicsUseCase(GetComicsParams(heroeId))
            .observeStatus()
    }

    private fun Flow<ResultStatus<List<ComicsViewData>>>.observeStatus() =
        viewModelScope.launch {
            collect { status ->
                _viewState.value = when (status) {
                    is Loading -> DetailsViewModelState.Loading
                    is Error -> DetailsViewModelState.Error
                    is Success -> DetailsViewModelState.Success(status.data)
                }
            }
        }

    sealed class DetailsViewModelState {
        object Loading : DetailsViewModelState()
        data class Success(val comics: List<ComicsViewData>) : DetailsViewModelState()
        object Error : DetailsViewModelState()
    }
}