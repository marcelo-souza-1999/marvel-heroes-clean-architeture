package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.domain.repository.HeroesRepository
import com.marcelo.marvelheroes.domain.usecases.GetHeroes
import io.mockk.mockk
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

    private val mockHeroesRepository: HeroesRepository = mockk()
    private val getHeroes: GetHeroes by inject()

    private val setupModule = module {
        single<GetHeroes> { GetHeroes(mockHeroesRepository) }
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
        val expected = GetHeroes(mockHeroesRepository)

        val actual = getHeroes

        assertEquals(expected::class, actual::class)
    }
}
