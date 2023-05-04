package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.usecases.GetComicsUseCaseImpl
import com.marcelo.marvelheroes.domain.usecases.GetHeroesGetHeroesUseCaseImpl
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetComicsUseCase
import com.marcelo.marvelheroes.domain.usecases.interfaces.GetHeroesUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class UseCaseModule {
    @Single
    fun provideGetHeroesUseCase(getHeroesUseCaseImpl: GetHeroesGetHeroesUseCaseImpl): GetHeroesUseCase {
        return getHeroesUseCaseImpl
    }

    @Single
    fun provideGetComicsUseCase(getComicsUseCaseImpl: GetComicsUseCaseImpl): GetComicsUseCase {
        return getComicsUseCaseImpl
    }
}