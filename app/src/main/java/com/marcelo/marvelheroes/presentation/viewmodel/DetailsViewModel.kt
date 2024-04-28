package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.model.FavoriteViewData
import com.marcelo.marvelheroes.domain.usecases.CheckFavorite
import com.marcelo.marvelheroes.domain.usecases.DeleteFavorite
import com.marcelo.marvelheroes.domain.usecases.GetComicsEvents
import com.marcelo.marvelheroes.domain.usecases.SaveFavorite
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewState
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    private val getComicsEvents: GetComicsEvents,
    private val saveFavorite: SaveFavorite,
    private val checkFavorite: CheckFavorite,
    private val deleteFavorite: DeleteFavorite
) : ViewModel() {

    private val _viewStateDetails =
        MutableStateFlow<State<List<DetailParentViewData>>>(State.Loading())
    val viewStateDetails = _viewStateDetails.asStateFlow()

    private val _viewStateSaveFavorite = MutableStateFlow<State<FavoriteViewData>>(State.Loading())
    val viewStateSaveFavorite = _viewStateSaveFavorite.asStateFlow()

    private val _viewStateCheckFavorite = MutableStateFlow<State<Boolean>>(State.Loading())
    val viewStateCheckFavorite = _viewStateCheckFavorite.asStateFlow()

    private val _viewStateDeleteFavorite =
        MutableStateFlow<State<Boolean>>(State.Loading())
    val viewStateDeleteFavorite = _viewStateDeleteFavorite.asStateFlow()

    private val _viewStateIsFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _viewStateIsFavorite

    fun getHeroesDetails(heroId: Int) = viewModelScope.launch {
        getComicsEvents(heroId)
            .collectViewStateList(_viewStateDetails)
    }

    fun saveHeroFavorite(
        heroId: Int,
        heroName: String,
        heroImageUrl: String
    ) = viewModelScope.launch {
        saveFavorite(
            heroId = heroId,
            nameHero = heroName,
            imageUrl = heroImageUrl
        ).collectViewState(_viewStateSaveFavorite)
    }

    fun checkHeroFavorite(heroId: Int) = viewModelScope.launch {
        checkFavorite(
            heroId = heroId
        ).collectViewState(_viewStateCheckFavorite)
    }

    fun deleteHeroFavorite(heroId: Int) = viewModelScope.launch {
        deleteFavorite(
            heroId = heroId
        ).collectViewState(_viewStateDeleteFavorite)
    }
}
