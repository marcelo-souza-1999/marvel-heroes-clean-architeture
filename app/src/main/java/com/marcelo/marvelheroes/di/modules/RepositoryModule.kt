package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.remote.datasource.RetrofitHeroesDataSourceImpl
import com.marcelo.marvelheroes.data.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class RepositoryModule {

    @Single
    fun providesHeroesRepository(
        remoteDataSource: HeroesRemoteDataSource,
        detailHeroesMapper: DetailHeroesMapper
    ): HeroesRepository {
        return HeroesRepositoryImpl(remoteDataSource, detailHeroesMapper)
    }

    @Single
    fun providesRemoteDataSource(dataSource: RetrofitHeroesDataSourceImpl): HeroesRemoteDataSource {
        return dataSource
    }
}