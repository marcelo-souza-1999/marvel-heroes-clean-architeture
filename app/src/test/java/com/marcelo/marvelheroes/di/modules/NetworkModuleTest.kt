package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.BuildConfig
import com.marcelo.marvelheroes.data.remote.interceptor.AuthorizationInterceptor
import com.marcelo.marvelheroes.utils.INVALID_URL
import com.marcelo.marvelheroes.utils.THOUSAND
import com.marcelo.marvelheroes.utils.TIMEOUT_SECONDS
import com.marcelo.marvelheroes.utils.VALID_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_NetworkModule as networkModule

class NetworkModuleTest : KoinComponent {

    private val loggingInterceptor by inject<HttpLoggingInterceptor>()
    private val authorizationInterceptor by inject<AuthorizationInterceptor>()
    private val okHttpClient by inject<OkHttpClient>()
    private val gsonConverterFactory by inject<GsonConverterFactory>()

    @Before
    fun setUp() {
        startKoin {
            modules(networkModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test logging interceptor`() {
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val expectedLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        assertEquals(expectedLevel, loggingInterceptor.level)
    }

    @Test
    fun `test authorization interceptor`() {
        assertEquals(BuildConfig.PUBLIC_KEY, authorizationInterceptor.publicKey)
        assertEquals(BuildConfig.PRIVATE_KEY, authorizationInterceptor.privateKey)
    }

    @Test
    fun `test okHttp client`() {
        assertEquals(TIMEOUT_SECONDS * THOUSAND, okHttpClient.connectTimeoutMillis.toLong())
        assertEquals(TIMEOUT_SECONDS * THOUSAND, okHttpClient.readTimeoutMillis.toLong())
        assertEquals(2, okHttpClient.interceptors.size)
    }

    @Test
    fun `test gson converter factory`() {
        assertEquals(GsonConverterFactory::class.java, gsonConverterFactory::class.java)
    }

    @Test
    fun `test Marvel API`() {
        assertEquals(BuildConfig.BASE_URL_API, VALID_URL)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test invalid base URL`() {
        Retrofit.Builder()
            .baseUrl(INVALID_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}
