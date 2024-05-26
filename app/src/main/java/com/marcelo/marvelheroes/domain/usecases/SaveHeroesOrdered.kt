package com.marcelo.marvelheroes.domain.usecases

import com.marcelo.marvelheroes.domain.mapper.SortHeroesMapper.mapToString
import com.marcelo.marvelheroes.domain.model.SortHeroesViewData
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class SaveHeroesOrdered(
    private val repository: LocalStorageRepository
) {
    suspend operator fun invoke(
        orderedPair: Pair<String, String>
    ): Flow<SortHeroesViewData> =
        flow {
            try {
                repository.saveSort(mapToString(orderedPair))
                emit(SortHeroesViewData.Success(orderedPair))
            } catch (e: Exception) {
                emit(SortHeroesViewData.Error(e))
            }
        }
}