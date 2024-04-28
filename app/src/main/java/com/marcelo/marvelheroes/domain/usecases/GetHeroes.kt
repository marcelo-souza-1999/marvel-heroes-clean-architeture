package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.PagingConfig
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import org.koin.core.annotation.Single

@Single
class GetHeroes(
    private val repository: HeroesRepository
) {
    operator fun invoke(
        query: String,
        pagingConfig: PagingConfig
    ) = repository.getCachedHeroes(
        query,
        pagingConfig
    )
}