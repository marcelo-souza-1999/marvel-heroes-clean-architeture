package com.marcelo.marvelheroes.data.remote.model

import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)
