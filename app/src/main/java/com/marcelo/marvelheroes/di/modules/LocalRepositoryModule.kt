package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.local.datasource.FavoritesHeroLocalDataSourceImpl
import com.marcelo.marvelheroes.data.local.repository.FavoritesHeroRepositoryImpl
import com.marcelo.marvelheroes.data.local.repository.LocalStorageRepositoryImpl
import com.marcelo.marvelheroes.domain.datasource.FavoritesHeroLocalDataSource
import com.marcelo.marvelheroes.domain.datasource.LocalStorageDataSource
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.domain.repository.LocalStorageRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class LocalRepositoryModule {

    @Single
    fun providesFavoritesHeroRepository(
        localDataSource: FavoritesHeroLocalDataSource,
    ): FavoritesHeroRepository {
        return FavoritesHeroRepositoryImpl(localDataSource)
    }

    @Single
    fun providesLocalDataSource(dataSource: FavoritesHeroLocalDataSourceImpl): FavoritesHeroLocalDataSource {
        return dataSource
    }

    @Single
    fun providesLocalStorageRepository(
        localStorageDataSource: LocalStorageDataSource
    ): LocalStorageRepository {
        return LocalStorageRepositoryImpl(localStorageDataSource)
    }
}