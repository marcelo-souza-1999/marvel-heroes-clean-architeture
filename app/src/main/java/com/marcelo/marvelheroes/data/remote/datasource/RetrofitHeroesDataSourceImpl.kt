package com.marcelo.marvelheroes.data.remote.datasource

import com.marcelo.marvelheroes.data.remote.api.MarvelApi
import com.marcelo.marvelheroes.data.remote.model.ComicsResponse
import com.marcelo.marvelheroes.data.remote.model.DataContainerResponse
import com.marcelo.marvelheroes.data.remote.model.EventsResponse
import com.marcelo.marvelheroes.data.remote.model.HeroesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class RetrofitHeroesDataSourceImpl(private val marvelApi: MarvelApi) : HeroesRemoteDataSource {

    override fun fetchHeroes(queries: Map<String, String>): Flow<DataContainerResponse<HeroesResponse>> {
        return flow {
            val response = marvelApi.getHeroes(queries)
            emit(response)
        }
    }

    override fun fetchComics(heroId: Int): Flow<DataContainerResponse<ComicsResponse>> {
        return flow {
            val response = marvelApi.getComics(heroId)
            emit(response)
        }
    }

    override fun fetchEvents(heroId: Int): Flow<DataContainerResponse<EventsResponse>> {
        return flow {
            val response = marvelApi.getEvents(heroId)
            emit(response)
        }
    }


    /* override suspend fun fetchHeroes(queries: Map<String, String>) = marvelApi.getHeroes(queries)

     override suspend fun fetchComics(heroId: Int) = marvelApi.getComics(heroId)

     override suspend fun fetchEvents(heroId: Int) = marvelApi.getEvents(heroId)
 */


    /*override suspend fun fetchHeroes(queries: Map<String, String>): HeroesPagingViewData {
        val dataPaging = marvelApi.getHeroes(queries).dataContainerHeroes
        val heroes = dataPaging.results.map {
            it.toHeroesViewData()
        }

        return HeroesPagingViewData(
            offset = dataPaging.offset,
            total = dataPaging.total,
            heroesList = heroes
        )
    }*/

    /*override suspend fun fetchComics(heroId: Int) =
        marvelApi.getComics(heroId).dataContainerHeroes.results.map {
            it.toComicsViewData()
        }

    override suspend fun fetchEvents(heroId: Int): List<EventsViewData> =
        marvelApi.getEvents(heroId).dataContainerHeroes.results.map {
            it.toEventsViewData()
        }*/
}
