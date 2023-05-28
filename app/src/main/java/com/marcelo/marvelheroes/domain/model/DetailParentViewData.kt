package com.marcelo.marvelheroes.domain.model

data class DetailParentViewData(
    val categories: String,
    val detailChildList: List<DetailChildViewData>,
)
