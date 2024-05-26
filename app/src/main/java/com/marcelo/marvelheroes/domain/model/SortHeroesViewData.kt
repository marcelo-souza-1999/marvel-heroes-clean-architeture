package com.marcelo.marvelheroes.domain.model

sealed interface SortHeroesViewData {
    data class Success(val sortingPair: Pair<String, String>) : SortHeroesViewData
    data class Error(val exception: Exception) : SortHeroesViewData
}