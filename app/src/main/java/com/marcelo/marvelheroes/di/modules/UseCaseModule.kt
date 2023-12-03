package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.factories.DetailParentFactory
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.CheckFavorite
import com.marcelo.marvelheroes.domain.usecases.DeleteFavorite
import com.marcelo.marvelheroes.domain.usecases.GetComicsEvents
import com.marcelo.marvelheroes.domain.usecases.GetHeroes
import com.marcelo.marvelheroes.domain.usecases.GetHeroesFavorite
import com.marcelo.marvelheroes.domain.usecases.SaveFavorite
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class UseCaseModule {
    @Single
    fun provideGetHeroesUseCase(repository: HeroesRepository) = GetHeroes(repository)

    @Single
    fun provideGetComicsUseCase(
        repository: HeroesRepository,
        factory: DetailParentFactory
    ) = GetComicsEvents(repository, factory)

    @Single
    fun provideGetFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = CheckFavorite(repository)

    @Single
    fun provideSaveFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = SaveFavorite(repository)

    @Single
    fun provideRemoveFavoriteUseCase(
        repository: FavoritesHeroRepository
    ) = DeleteFavorite(repository)

    @Single
    fun providesGetHeroesFavorite(
        repository: FavoritesHeroRepository
    ) = GetHeroesFavorite(repository)
}