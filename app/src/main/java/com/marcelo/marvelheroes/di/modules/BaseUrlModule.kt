package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.BuildConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@Single
class BaseUrlModule : KoinComponent {

    @Single
    @Named("baseUrl")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL_API
}