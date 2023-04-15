package com.marcelo.marvelheroes.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataContainerResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data")
    val dataContainerHeroes: DataContainerHeroesResponse
)
