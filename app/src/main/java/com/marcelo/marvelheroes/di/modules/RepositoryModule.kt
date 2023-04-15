package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.remote.datasource.RetrofitHeroesDataSourceImpl
import com.marcelo.marvelheroes.data.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class RepositoryModule {

    @Single
    fun providesHeroesRepository(repositoryImpl: HeroesRepositoryImpl): HeroesRepository {
        return repositoryImpl
    }

    @Single
    fun providesRemoteDataSource(dataSource: RetrofitHeroesDataSourceImpl): HeroesRemoteDataSource {
        return dataSource
    }
}