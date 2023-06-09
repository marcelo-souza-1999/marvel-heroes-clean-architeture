package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.data.remote.datasource.HeroesRemoteDataSource
import com.marcelo.marvelheroes.data.repository.HeroesRepositoryImpl
import com.marcelo.marvelheroes.domain.mapper.DetailHeroesMapper
import com.marcelo.marvelheroes.domain.repository.HeroesRepository
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

class RepositoryModuleTest : KoinTest {

    private lateinit var mockRemoteDataSource: HeroesRemoteDataSource
    private lateinit var mockMapper: DetailHeroesMapper
    private val heroesRepository: HeroesRepository by inject()

    private val setupModule = module {
        mockRemoteDataSource = mockk()
        mockMapper = mockk()
        single<HeroesRepository> {
            HeroesRepositoryImpl(
                remoteDataSource = get(),
                detailHeroesMapper = get()
            )
        }
        single { mockk<HeroesRemoteDataSource>() }
        single { mockk<DetailHeroesMapper>() }
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
        val expected = HeroesRepositoryImpl(
            remoteDataSource = mockRemoteDataSource,
            detailHeroesMapper = mockMapper
        )

        val actual = heroesRepository

        assertEquals(expected::class, actual::class)
    }
}
