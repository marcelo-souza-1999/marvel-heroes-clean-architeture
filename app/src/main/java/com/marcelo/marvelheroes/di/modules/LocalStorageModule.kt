package com.marcelo.marvelheroes.di.modules

import android.content.Context
import com.marcelo.marvelheroes.data.local.datasource.LocalStorageDataSourceImpl
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Single
class LocalStorageModule {

    @Single
    fun providesLocalStorageDataSource(context: Context): LocalStorageDataSourceImpl {
        return LocalStorageDataSourceImpl(context)
    }
}