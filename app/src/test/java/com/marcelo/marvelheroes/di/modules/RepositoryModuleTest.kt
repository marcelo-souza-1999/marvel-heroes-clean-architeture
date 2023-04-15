package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.repository.interfaces.HeroesRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class RepositoryModuleTest : KoinTest {

    private val mockRemoteDataSource: HeroesRemoteDataSource = mock()
    private val heroesRepository: HeroesRepository by inject()

    private val setupModule = module {
        single<HeroesRepository> { HeroesRepositoryImpl(get()) }
        single { mock<HeroesRemoteDataSource>() }
    }

    @Before
    fun setup() {
        startKoin { modules(setupModule) }
    }

    @After
    fun before() {
        stopKoin()
    }


    @Test
    fun `test providesHeroesRepository`() {
        val expected = HeroesRepositoryImpl(mockRemoteDataSource)

        val actual = heroesRepository

        assertEquals(expected::class, actual::class)
    }
}
