package com.marcelo.marvelheroes.presentation.viewmodel.viewstate

sealed interface State<T> {
    open class Error<T>(val errorType: ErrorType, val throwable: Throwable? = null) : State<T>
    data class Success<T>(val data: T) : State<T>
    class Loading<T> : State<T>
}

sealed class ErrorType {
    object NetworkError : ErrorType()
    object ServerError : ErrorType()
    object GenericError : ErrorType()
}