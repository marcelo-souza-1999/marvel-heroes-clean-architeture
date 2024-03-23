package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteDataSourceImpl
import com.marcelo.marvelheroes.data.remote.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.domain.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class RemoteRepositoryModule {

    @Single
    fun providesHeroesRepository(
        remoteDataSource: HeroesRemoteDataSource,
        database: MarvelDatabase,
        detailHeroesMapper: DetailHeroesMapper
    ): HeroesRepository {
        return HeroesRepositoryImpl(remoteDataSource, database, detailHeroesMapper)
    }

    @Single
    fun providesRemoteDataSource(dataSource: HeroesRemoteDataSourceImpl): HeroesRemoteDataSource {
        return dataSource
    }

}