package com.marcelo.marvelheroes.utils.states

sealed class ResultStatus<out T> {

    object Loading : ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val throwable: Throwable) : ResultStatus<Nothing>()
}
