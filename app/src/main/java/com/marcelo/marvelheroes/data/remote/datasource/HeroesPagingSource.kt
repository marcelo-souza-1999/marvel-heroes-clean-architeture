package com.marcelo.marvelheroes.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marcelo.marvelheroes.domain.model.HeroesViewData
import com.marcelo.marvelheroes.extensions.toHeroesViewData
import kotlinx.coroutines.flow.singleOrNull

class HeroesPagingSource(
    private val remoteDataSource: HeroesRemoteDataSource,
    private val query: String
) : PagingSource<Int, HeroesViewData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroesViewData> {
        val offset = params.key ?: ZERO

        val queries = hashMapOf(
            OFFSET to offset.toString()
        )

        if (query.isNotEmpty()) {
            queries[NAME_STARTS_WITH] = query
        }

        return try {
            val response = remoteDataSource.fetchHeroes(queries).singleOrNull()
            val requestOffset = response?.dataContainerHeroes?.offset ?: ZERO
            val totalHeroes = response?.dataContainerHeroes?.total ?: ZERO

            LoadResult.Page(
                data = response?.dataContainerHeroes?.results?.map { it.toHeroesViewData() }
                    ?: emptyList(),
                prevKey = null,
                nextKey = if (requestOffset < totalHeroes) requestOffset + LIMIT_HEROES else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HeroesViewData>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT_HEROES) ?: anchorPage?.nextKey ?: ZERO
        } ?: ZERO


    companion object {
        private const val ZERO = 0
        private const val LIMIT_HEROES = 20
        private const val OFFSET = "offset"
        private const val NAME_STARTS_WITH = "nameStartsWith"
    }
}
