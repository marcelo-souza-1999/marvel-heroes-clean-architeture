package com.marcelo.marvelheroes.domain.usecases.interfaces

import androidx.paging.PagingData
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCaseImpl.Companion.GetHeroesParams
import kotlinx.coroutines.flow.Flow

interface GetHeroesUseCase {
    operator fun invoke(
        params: GetHeroesParams
    ): Flow<PagingData<HeroesViewData>>
}