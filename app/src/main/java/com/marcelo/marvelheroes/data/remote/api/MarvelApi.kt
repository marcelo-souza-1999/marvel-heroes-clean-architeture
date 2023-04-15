package com.marcelo.marvelheroes.data.remote.api

import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET(GET_HEROES)
    suspend fun getHeroes(
        @QueryMap
        queries: Map<String, String>
    ): DataContainerResponse

    companion object {
        private const val GET_HEROES = "characters"
    }
}