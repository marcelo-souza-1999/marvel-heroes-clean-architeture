package com.marcelo.marvelheroes.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataContainerHeroesResponse(
    @SerializedName("results")
    val results: List<HeroesResponse>
)
