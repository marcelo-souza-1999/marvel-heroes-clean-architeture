package com.marcelo.marvelheroes.utils.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

abstract class PagingUseCase<in Params, Return : Any> {
    operator fun invoke(params: Params): Flow<PagingData<Return>> = createFlowObservable(params)

    protected abstract fun createFlowObservable(params: Params): Flow<PagingData<Return>>
}