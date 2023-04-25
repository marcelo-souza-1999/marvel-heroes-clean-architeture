package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.BuildConfig
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_BaseUrlModule as baseUrlModule

class BaseUrlModuleTest : KoinTest {

    private lateinit var baseUrl: String

    @Before
    fun setUp() {
        startKoin {
            modules(baseUrlModule)
        }
        baseUrl = get(named("baseUrl"))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `baseUrl should be equal to BuildConfig BASE_URL_API`() {
        assertEquals(BuildConfig.BASE_URL_API, baseUrl)
    }
}