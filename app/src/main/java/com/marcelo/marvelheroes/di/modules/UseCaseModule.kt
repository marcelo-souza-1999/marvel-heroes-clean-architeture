package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.usecases.GetComicsEventsEventsUseCaseImpl
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCaseImpl
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsEventsUseCase
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class UseCaseModule {
    @Single
    fun provideGetHeroesUseCase(getHeroesUseCaseImpl: GetHeroesUseCaseImpl): GetHeroesUseCase {
        return getHeroesUseCaseImpl
    }

    @Single
    fun provideGetComicsUseCase(getComicsEventsUseCaseImpl: GetComicsEventsEventsUseCaseImpl): GetComicsEventsUseCase {
        return getComicsEventsUseCaseImpl
    }
}