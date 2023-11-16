package com.marcelo.marvelheroes.domain.model

import androidx.annotation.DrawableRes
import com.marcelo.marvelheroes.presentation.adapters.utils.ListItem

data class FavoriteViewData(
    @DrawableRes val favoriteIcon: Int
)

data class FavoriteItemData(
    val id : Int,
    val name : String,
    val imageUrl: String,
    override val key: Long = id.toLong()
) : ListItem
