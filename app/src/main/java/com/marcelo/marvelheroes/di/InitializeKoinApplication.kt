package com.marcelo.marvelheroes.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
//import org.koin.ksp.generated.defaultModule as koinModule
class InitializeKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            //modules(modules = koinModule)
        }
    }
}