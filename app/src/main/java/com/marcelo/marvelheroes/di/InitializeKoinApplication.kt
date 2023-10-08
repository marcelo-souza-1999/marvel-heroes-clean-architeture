package com.marcelo.marvelheroes.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_BaseUrlModule as baseUrlModule
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_CoroutineModule as coroutineModule
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_NetworkModule as networkModule
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_DatabaseModule as databaseModule
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_RemoteRepositoryModule as remoteRepositoryModule
import org.koin.ksp.generated.com_marcelo_marvelheroes_di_modules_LocalRepositoryModule as localRepositoryModule
import org.koin.ksp.generated.defaultModule as marvelKoinModule

class InitializeKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                listOf(
                    baseUrlModule,
                    marvelKoinModule,
                    networkModule,
                    databaseModule,
                    remoteRepositoryModule,
                    localRepositoryModule,
                    coroutineModule
                )
            )
        }
    }
}