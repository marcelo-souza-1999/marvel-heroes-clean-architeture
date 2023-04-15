package com.marcelo.marvelheroes.data.remote.datasource

import com.marcelo.marvelheroes.data.remote.api.MarvelApi
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import org.koin.core.annotation.Single

@Single
class RetrofitHeroesDataSourceImpl(private val marvelApi: MarvelApi) : HeroesRemoteDataSource {
    override suspend fun fetchHeroes(queries: Map<String, String>) = marvelApi.getHeroes(queries)
}
