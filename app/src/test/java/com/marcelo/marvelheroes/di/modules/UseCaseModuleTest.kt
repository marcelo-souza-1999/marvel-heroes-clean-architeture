package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.GetHeroesUseCase
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

class UseCaseModuleTest : KoinTest {

    private val mockHeroesRepository: HeroesRepository = mock()
    private val getHeroesUseCase: GetHeroesUseCase by inject()

    private val setupModule = module {
        single<GetHeroesUseCase> { GetHeroesUseCase(mockHeroesRepository) }
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
    fun `test provideGetHeroesUseCase`() {
        val expected = GetHeroesUseCase(mockHeroesRepository)

        val actual = getHeroesUseCase

        assertEquals(expected::class, actual::class)
    }
}
