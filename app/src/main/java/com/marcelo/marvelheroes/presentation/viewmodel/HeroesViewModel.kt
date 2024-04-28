package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.domain.usecases.GetHeroes
import com.marcelo.marvelheroes.extensions.emptyString
import kotlinx.coroutines.flow.Flow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HeroesViewModel(
    private val getHeroes: GetHeroes,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val DEFAULT_PAGE_SIZE = 20
    private val TEXT_SEARCH_KEY = "textSearch"

    init {
        if (!savedStateHandle.contains(TEXT_SEARCH_KEY)) {
            savedStateHandle[TEXT_SEARCH_KEY] = emptyString()
        }
    }

    fun getTextSearch(): String {
        return savedStateHandle[TEXT_SEARCH_KEY] ?: emptyString()
    }

    fun setTextSearch(query: String) {
        savedStateHandle[TEXT_SEARCH_KEY] = query
    }

    fun getPagingHeroes(): Flow<PagingData<HeroesViewData>> =
        getHeroes(
            query = getTextSearch(),
            pagingConfig = getDefaultPagingConfig()
        ).cachedIn(viewModelScope)

    private fun getDefaultPagingConfig() = PagingConfig(
        pageSize = DEFAULT_PAGE_SIZE
    )
}