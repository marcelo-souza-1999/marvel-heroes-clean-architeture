package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.factories.DetailParentFactory
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.CheckFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.DeleteFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCase
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class UseCaseModule {
    @Single
    fun provideGetHeroesUseCase(repository: HeroesRepository) = GetHeroesUseCase(repository)

    @Single
    fun provideGetComicsUseCase(
        repository: HeroesRepository,
        factory: DetailParentFactory
    ) = GetComicsEventsUseCase(repository, factory)

    @Single
    fun provideGetFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = CheckFavoriteUseCase(repository)

    @Single
    fun provideSaveFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = SaveFavoriteUseCase(repository)

    @Single
    fun provideRemoveFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = DeleteFavoriteUseCase(repository)
}