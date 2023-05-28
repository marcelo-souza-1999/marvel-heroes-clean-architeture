package com.marcelo.marvelheroes.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesGetHeroesUseCaseImpl.Companion.GetHeroesParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import com.marcelo.marvelheroes.utils.usecase.PagingUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetHeroesGetHeroesUseCaseImpl(
    private val repository: HeroesRepository
) : PagingUseCase<GetHeroesParams, HeroesViewData>(), GetHeroesUseCase {

    override fun createFlowObservable(params: GetHeroesParams): Flow<PagingData<HeroesViewData>> {
        val heroesPagingSource = repository.getHeroes(params.query)
        return Pager(config = params.pagingConfig) {
            heroesPagingSource
        }.flow
    }

    companion object {
        data class GetHeroesParams(val query: String, val pagingConfig: PagingConfig)
    }
}