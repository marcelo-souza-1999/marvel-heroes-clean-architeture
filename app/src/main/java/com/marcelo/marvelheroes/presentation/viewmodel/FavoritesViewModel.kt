package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.FavoriteItemData
import com.marcelo.marvelheroes.domain.usecases.GetHeroesFavorite
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel(
    private val getHeroesFavorite: GetHeroesFavorite
) : ViewModel() {

    private val _viewStateFavorites =
        MutableStateFlow<State<List<FavoriteItemData>>>(State.Loading())
    val viewStateFavorites = _viewStateFavorites.asStateFlow()

    fun getHeroesFavorites() = viewModelScope.launch {
        getHeroesFavorite()
            .collectViewStateList(_viewStateFavorites)
    }
}