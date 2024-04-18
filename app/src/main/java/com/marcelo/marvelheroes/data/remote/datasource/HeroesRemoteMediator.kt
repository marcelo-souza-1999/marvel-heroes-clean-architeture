package com.marcelo.marvelheroes.data.remote.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.data.local.entitys.HeroesEntity
import com.marcelo.marvelheroes.data.local.entitys.RemoteKeyEntity
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.extensions.getHttpsUrl
import kotlinx.coroutines.flow.singleOrNull
import okio.IOException


@OptIn(ExperimentalPagingApi::class)
class HeroesRemoteMediator(
    private val query: String,
    private val orderBy: String,
    private val database: MarvelDatabase,
    private val dataSource: HeroesRemoteDataSource
) : RemoteMediator<Int, HeroesEntity>() {

    private val heroDao = database.createHeroDao()
    private val remoteKeyDao = database.createRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeroesEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                REFRESH -> ZERO
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKey()
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val queries = hashMapOf(
                OFFSET to offset.toString()
            )
            if (query.isNotEmpty()) {
                queries[NAME_STARTS_WITH] = query
            }
            if (orderBy.isNotEmpty()) {
                queries[ORDER_BY] = query
            }
            val heroesPaging = dataSource.fetchHeroes(queries).singleOrNull()
            val responseOffset = heroesPaging?.dataContainerHeroes?.offset ?: ZERO
            val totalHeroes = heroesPaging?.dataContainerHeroes?.total ?: ZERO

            database.withTransaction {
                if (loadType == REFRESH) {
                    remoteKeyDao.deleteALl()
                    heroDao.deleteAll()
                }

                remoteKeyDao.insertOrReplace(
                    remoteKeyEntity = RemoteKeyEntity(
                        nextKey = responseOffset + state.config.pageSize
                    )
                )

                heroesPaging?.dataContainerHeroes?.results?.map { data ->
                    HeroesEntity(
                        id = data.id,
                        name = data.name,
                        imageUrl = data.thumbnail.getHttpsUrl()
                    )
                }?.let { heroesEntity ->
                    heroDao.insertAll(heroes = heroesEntity)
                }
            }

            MediatorResult.Success(endOfPaginationReached = responseOffset >= totalHeroes)

        } catch (exceptionIO: IOException) {
            MediatorResult.Error(exceptionIO)
        } catch (exceptionHttp: Exception) {
            MediatorResult.Error(exceptionHttp)
        }
    }

    private companion object {
        const val ZERO = 0
        const val OFFSET = "offset"
        const val NAME_STARTS_WITH = "nameStartsWith"
        const val ORDER_BY = "orderBy"
    }
}