package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.mapper.SortHeroesMapper.mapToPair
import com.marcelo.marvelheroes.domain.model.SortHeroesViewData
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class GetHeroesOrdered(
    private val repository: LocalStorageRepository
) {
    suspend operator fun invoke(): Flow<SortHeroesViewData> = flow {
        try {
            repository.sort.collect { sortOrder ->
                emit(SortHeroesViewData.Success(mapToPair(sortOrder)))
            }
        } catch (e: Exception) {
            emit(SortHeroesViewData.Error(e))
        }
    }
}