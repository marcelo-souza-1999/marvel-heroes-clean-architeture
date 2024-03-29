package com.marcelo.marvelheroes.data.remote.model

import com.google.gson.annotations.SerializedName

data class DataContainerHeroesResponse<T>(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("results")
    val results: List<T>
)
