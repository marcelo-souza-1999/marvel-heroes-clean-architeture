package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single

@Single
class GetHeroes(
    private val repository: HeroesRepository,
    private val localStorageRepository: LocalStorageRepository
) {
    operator fun invoke(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<HeroesViewData>> {
        val orderBy = runBlocking { localStorageRepository.sort.first() }

        return repository.getCachedHeroes(
            query,
            orderBy,
            pagingConfig
        )
    }
}