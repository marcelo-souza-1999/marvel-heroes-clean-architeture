package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.SortHeroesViewData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesOrdered
import com.marcelo.marvelheroes.domain.usecases.SaveHeroesOrdered
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SortHeroesViewModel(
    private val getHeroesOrdered: GetHeroesOrdered,
    private val saveHeroesOrdered: SaveHeroesOrdered
) : ViewModel() {

    private val _viewStateSaveSortHeroes =
        MutableStateFlow<State<SortHeroesViewData>>(State.Loading())
    val viewStateSaveSortHeroes = _viewStateSaveSortHeroes.asStateFlow()

    private val _viewStateGetSortHeroes =
        MutableStateFlow<State<SortHeroesViewData>>(State.Loading())
    val viewStateGetSortHeroes = _viewStateGetSortHeroes.asStateFlow()

    init {
        getOrderHeroes()
    }

    private fun getOrderHeroes() = viewModelScope.launch {
        getHeroesOrdered().collectViewState(_viewStateGetSortHeroes)
    }

    fun saveOrderHeroes(
        orderBy: String, orderOptions: String
    ) = viewModelScope.launch {
        saveHeroesOrdered(
            orderedPair = orderBy to orderOptions
        ).collectViewState(_viewStateSaveSortHeroes)
    }
}