package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesGetHeroesUseCaseImpl.Companion.GetHeroesParams
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HeroesViewModel(
    private val getHeroesUseCase: GetHeroesUseCase
) : ViewModel() {

    fun getPagingHeroes(query: String): Flow<PagingData<HeroesViewData>> =
        getHeroesUseCase(
            GetHeroesParams(
                query = query,
                pagingConfig = getDefaultPagingConfig()
            )
        ).cachedIn(viewModelScope)

    private fun getDefaultPagingConfig() = PagingConfig(
        pageSize = DEFAULT_PAGE_SIZE
    )

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}