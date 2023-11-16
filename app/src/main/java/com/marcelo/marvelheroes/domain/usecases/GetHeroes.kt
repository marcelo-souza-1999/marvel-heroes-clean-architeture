package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetHeroes(
    private val repository: HeroesRepository
) {
    operator fun invoke(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<HeroesViewData>> {
        val heroesPagingSource = repository.getHeroes(query)
        return Pager(config = pagingConfig) {
            heroesPagingSource
        }.flow
    }
}