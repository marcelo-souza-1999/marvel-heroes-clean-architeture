package com.marcelo.marvelheroes.data.remote.api

import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET(GET_HEROES)
    suspend fun getHeroes(
        @QueryMap
        queries: Map<String, String>
    ): DataContainerResponse<HeroesResponse>

    @GET(GET_COMICS)
    suspend fun getComics(
        @Path(GET_HEROES_ID)
        characterId: Int
    ): DataContainerResponse<ComicsResponse>

    private companion object {
        const val GET_HEROES_ID = "characterId"
        const val GET_HEROES = "characters"
        const val GET_COMICS = "characters/{characterId}/comics"
    }
}