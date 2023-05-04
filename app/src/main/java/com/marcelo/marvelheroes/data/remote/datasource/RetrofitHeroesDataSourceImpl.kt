package com.marcelo.marvelheroes.data.remote.datasource

import com.marcelo.marvelheroes.data.remote.api.MarvelApi
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.model.HeroesPagingViewData
import com.marcelo.marvelheroes.extensions.toComicsViewData
import com.marcelo.marvelheroes.extensions.toHeroesViewData
import org.koin.core.annotation.Single

@Single
class RetrofitHeroesDataSourceImpl(private val marvelApi: MarvelApi) : HeroesRemoteDataSource {

    override suspend fun fetchHeroes(queries: Map<String, String>): HeroesPagingViewData {
        val dataPaging = marvelApi.getHeroes(queries).dataContainerHeroes
        val heroes = dataPaging.results.map {
            it.toHeroesViewData()
        }

        return HeroesPagingViewData(
            offset = dataPaging.offset,
            total = dataPaging.total,
            heroesList = heroes
        )
    }

    override suspend fun fetchComics(heroId: Int) =
        marvelApi.getComics(heroId).dataContainerHeroes.results.map {
            it.toComicsViewData()
        }

}
