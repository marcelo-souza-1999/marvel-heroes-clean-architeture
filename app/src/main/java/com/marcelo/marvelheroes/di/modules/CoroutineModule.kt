package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchers
import com.marcelo.marvelheroes.utils.coroutines.CoroutinesDispatchersImpl
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@Single
class CoroutineModule : KoinComponent {
    @Single
    fun provideCoroutinesDispatchers(): CoroutinesDispatchers = CoroutinesDispatchersImpl()
}