package com.marcelo.marvelheroes.utils.usecase

import com.marcelo.marvelheroes.utils.states.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in Params, Return> {

    operator fun invoke(params: Params): Flow<ResultStatus<Return>> = flow {
        emit(ResultStatus.Loading)
        emit(runUseCase(params))
    }.catch { throwable ->
        emit(ResultStatus.Error(throwable))
    }

    @Throws(Exception::class)
    protected abstract suspend fun runUseCase(params: Params): ResultStatus<Return>
}
