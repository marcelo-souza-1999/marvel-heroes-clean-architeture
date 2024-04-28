package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcelo.marvelheroes.R
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

    private val defaultPageSize = R.dimen.twenty
    private val textSearchKey = "textSearch"

    init {
        if (!savedStateHandle.contains(textSearchKey)) {
            savedStateHandle[textSearchKey] = emptyString()
        }
    }

    fun getTextSearch(): String {
        return savedStateHandle[textSearchKey] ?: emptyString()
    }

    fun setTextSearch(query: String) {
        savedStateHandle[textSearchKey] = query
    }

    fun getPagingHeroes(): Flow<PagingData<HeroesViewData>> =
        getHeroes(
            query = getTextSearch(),
            pagingConfig = getDefaultPagingConfig()
        ).cachedIn(viewModelScope)

    private fun getDefaultPagingConfig() = PagingConfig(
        pageSize = defaultPageSize
    )
}