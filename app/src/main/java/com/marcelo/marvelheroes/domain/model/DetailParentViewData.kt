package com.marcelo.marvelheroes.domain.model

import androidx.annotation.StringRes

data class DetailParentViewData(
    @StringRes
    val categoriesResId: Int,
    val detailChildList: List<DetailChildViewData>,
)
