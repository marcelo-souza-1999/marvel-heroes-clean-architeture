package com.marcelo.marvelheroes.presentation.viewmodel.viewstate

import com.marcelo.marvelheroes.extensions.isConnectionError
import com.marcelo.marvelheroes.extensions.isServerError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

internal suspend fun <T> Flow<T>.collectViewState(viewState: MutableStateFlow<State<T>>) =
    onStart {
        viewState.value = State.Loading()
    }.catch {
        viewState.value = when {
            it.isConnectionError() -> State.Error(ErrorType.NetworkError)
            it.isServerError() -> State.Error(ErrorType.ServerError)
            else -> State.Error(ErrorType.GenericError)
        }
    }.collect {
        viewState.value = State.Success(it)
    }

internal suspend fun <T> Flow<List<T>>.collectViewStateList(viewState: MutableStateFlow<State<List<T>>>) =
    onStart {
        viewState.value = State.Loading()
    }.catch { exception ->
        viewState.value = when {
            exception.isConnectionError() -> {
                State.Error(ErrorType.NetworkError)
            }

            exception.isServerError() -> State.Error(ErrorType.ServerError)
            else -> State.Error(ErrorType.GenericError)
        }
    }.collect {
        viewState.value = State.Success(it)
    }
