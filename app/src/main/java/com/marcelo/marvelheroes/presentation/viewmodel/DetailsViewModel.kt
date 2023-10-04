package com.marcelo.marvelheroes.presentation.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.marvelheroes.R
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.domain.usecases.DeleteFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.GetFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCase
import com.marcelo.marvelheroes.utils.states.ResultStatus
import com.marcelo.marvelheroes.utils.states.ResultStatus.Error
import com.marcelo.marvelheroes.utils.states.ResultStatus.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsViewModel(
    private val getComicsEventsUseCase: GetComicsEventsUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _viewStateDetails = MutableStateFlow(DetailsViewState())
    val viewState = _viewStateDetails.asStateFlow()

    private val _viewStateFavorite = MutableStateFlow(FavoriteViewState())
    val viewStateFavorite = _viewStateFavorite.asStateFlow()

    fun getHeroesDetails(heroId: Int) = viewModelScope.launch {
        getComicsEventsUseCase(heroId).onStart {
            _viewStateDetails.value = DetailsViewState(isLoading = true)
        }.onEach(::handleSuccessDetails).launchIn(viewModelScope)
    }

    fun saveHeroFavorite(heroId: Int, heroName: String, heroImageUrl: String) =
        viewModelScope.launch {
            saveFavoriteUseCase(
                heroId = heroId, nameHero = heroName, imageUrl = heroImageUrl
            ).onStart { _viewStateFavorite.value = FavoriteViewState(isLoading = true) }
                .onEach(::handleSaveFavorite).launchIn(viewModelScope)
        }

    fun checkFavorite(heroId: Int) = viewModelScope.launch {
        getFavoriteUseCase(
            heroId = heroId
        ).onStart { _viewStateFavorite.value = FavoriteViewState(isLoading = true) }
            .onEach(::handleCheckFavorite).launchIn(viewModelScope)
    }

    fun deleteFavorite(heroId: Int) = viewModelScope.launch {
        deleteFavoriteUseCase(
            heroId = heroId
        ).onStart { _viewStateFavorite.value = FavoriteViewState(isLoading = true) }
            .onEach(::handleRemoveFavorite).launchIn(viewModelScope)
    }

    private fun handleSuccessDetails(resultStatus: ResultStatus<List<DetailParentViewData>>) {
        if (resultStatus is Error) _viewStateDetails.value = DetailsViewState(error = true)

        if (resultStatus is Success) {
            _viewStateDetails.value =
                if (resultStatus.data.isNotEmpty()) DetailsViewState(success = resultStatus.data)
                else DetailsViewState(empty = true)
        }
    }

    private fun handleCheckFavorite(resultStatus: ResultStatus<Boolean>) {
        if (resultStatus is Success) {
            _viewStateFavorite.value = FavoriteViewState(
                successIcon = if (resultStatus.data) {
                    R.drawable.ic_favorite_hero
                } else {
                    R.drawable.ic_not_favorite_hero
                }
            )
        }
    }

    private fun handleRemoveFavorite(resultStatus: ResultStatus<Unit>) {
        if (resultStatus is Success) _viewStateFavorite.value = FavoriteViewState(
            successIcon = R.drawable.ic_not_favorite_hero
        )
    }

    private fun handleSaveFavorite(resultStatus: ResultStatus<Unit>) {
        if (resultStatus is Success) _viewStateFavorite.value =
            FavoriteViewState(successIcon = R.drawable.ic_favorite_hero)
    }

    companion object {
        data class DetailsViewState(
            val isLoading: Boolean = true,
            val empty: Boolean = false,
            val error: Boolean = false,
            val success: List<DetailParentViewData> = emptyList()
        )

        data class FavoriteViewState(
            val isLoading: Boolean = false,
            @DrawableRes val successIcon: Int = R.drawable.ic_not_favorite_hero
        )
    }
}