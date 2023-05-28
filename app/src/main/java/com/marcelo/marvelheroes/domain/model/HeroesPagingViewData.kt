package com.marcelo.marvelheroes.domain.model

data class HeroesPagingViewData(
    val offset: Int,
    val total: Int,
    val heroesList: List<HeroesViewData>
)