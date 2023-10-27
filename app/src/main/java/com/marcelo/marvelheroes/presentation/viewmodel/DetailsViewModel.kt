package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.model.FavoriteViewData
import com.marcelo.marvelheroes.domain.usecases.CheckFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.DeleteFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCase
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.State
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewState
import com.marcelo.marvelheroes.presentation.viewmodel.viewstate.collectViewStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    private val getComicsEventsUseCase: GetComicsEventsUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _viewStateDetails =
        MutableStateFlow<State<List<DetailParentViewData>>>(State.Loading())
    val viewState = _viewStateDetails.asStateFlow()

    private val _viewStateSaveFavorite = MutableStateFlow<State<FavoriteViewData>>(State.Loading())
    val viewStateSaveFavorite = _viewStateSaveFavorite.asStateFlow()

    private val _viewStateCheckFavorite = MutableStateFlow<State<Boolean>>(State.Loading())
    val viewStateCheckFavorite = _viewStateCheckFavorite.asStateFlow()

    private val _viewStateDeleteFavorite =
        MutableStateFlow<State<Boolean>>(State.Loading())
    val viewStateDeleteFavorite = _viewStateDeleteFavorite.asStateFlow()

    fun getHeroesDetails(heroId: Int) = viewModelScope.launch {
        getComicsEventsUseCase(heroId)
            .collectViewStateList(_viewStateDetails)
    }

    fun saveHeroFavorite(
        heroId: Int,
        heroName: String,
        heroImageUrl: String
    ) = viewModelScope.launch {
        saveFavoriteUseCase(
            heroId = heroId,
            nameHero = heroName,
            imageUrl = heroImageUrl
        ).collectViewState(_viewStateSaveFavorite)
    }

    fun checkFavorite(heroId: Int) = viewModelScope.launch {
        checkFavoriteUseCase(
            heroId = heroId
        ).collectViewState(_viewStateCheckFavorite)
    }

    fun deleteFavorite(heroId: Int) = viewModelScope.launch {
        deleteFavoriteUseCase(
            heroId = heroId
        ).collectViewState(_viewStateDeleteFavorite)
    }
}
