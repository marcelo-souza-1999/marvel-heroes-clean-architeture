package com.marcelo.marvelheroes.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailsHeroesArgViewData(
    val heroeId: Int,
    val name: String,
    val imageUrl: String
) : Parcelable