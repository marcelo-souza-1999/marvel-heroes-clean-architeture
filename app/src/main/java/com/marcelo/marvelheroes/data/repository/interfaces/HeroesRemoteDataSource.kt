package com.marcelo.marvelheroes.data.repository.interfaces

import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse

interface HeroesRemoteDataSource {
    suspend fun fetchHeroes(queries: Map<String, String>): DataContainerResponse
}