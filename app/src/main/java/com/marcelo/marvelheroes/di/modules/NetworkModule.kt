package com.marcelo.marvelheroes.di.modules

import com.marcelo.marvelheroes.BuildConfig
import com.marcelo.marvelheroes.data.remote.api.MarvelApi
import com.marcelo.marvelheroes.data.remote.interceptor.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@Single
class NetworkModule : KoinComponent {

    @Single
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Single
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().setLevel(level)
    }

    @Single
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor = AuthorizationInterceptor(
        publicKey = BuildConfig.PUBLIC_KEY,
        privateKey = BuildConfig.PRIVATE_KEY
    )

    @Single
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Single
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        @Named("baseUrl") baseUrl: String
    ): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(MarvelApi::class.java)
    }

    companion object {
        private const val TIMEOUT_SECONDS = 10L
    }
}
