package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.factories.DetailParentFactory
import com.marcelo.marvelheroes.domain.repository.FavoritesHeroRepository
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.DeleteFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.GetFavoriteUseCase
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCase
import com.marcelo.marvelheroes.domain.usecases.SaveFavoriteUseCase
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
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
        factory: DetailParentFactory,
        dispatchers: CoroutinesDispatchers
    ) = GetComicsEventsUseCase(repository, factory, dispatchers)

    @Single
    fun provideGetFavoriteUseCase(
        repository: FavoritesHeroRepository,
        dispatchers: CoroutinesDispatchers
    ) = GetFavoriteUseCase(repository, dispatchers)

    @Single
    fun provideSaveFavoriteUseCase(
        repository: FavoritesHeroRepository,
        dispatchers: CoroutinesDispatchers
    ) = SaveFavoriteUseCase(repository, dispatchers)

    @Single
    fun provideRemoveFavoriteUseCase(
        repository: FavoritesHeroRepository,
        dispatchers: CoroutinesDispatchers
    ) = DeleteFavoriteUseCase(repository, dispatchers)
}