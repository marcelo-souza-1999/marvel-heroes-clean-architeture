package com.marcelo.marvelheroes.data.remote.model

import com.google.gson.annotations.SerializedName
data class HeroesResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)