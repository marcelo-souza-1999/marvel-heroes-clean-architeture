package com.marcelo.marvelheroes.di.modules

import android.content.Context
import androidx.room.Room
import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.utils.constants.DATABASE_NAME
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@Single
class DatabaseModule : KoinComponent {

    @Single
    fun providesDatabase(context: Context) = Room.databaseBuilder(
        context = context, klass = MarvelDatabase::class.java, name = DATABASE_NAME
    ).build()


    @Single
    fun providesFavoriteDao(database: MarvelDatabase) = database.createFavoriteDao()
    fun providesHeroDao(database: MarvelDatabase) = database.createHeroDao()
    fun providesRemoteKeyDao(database: MarvelDatabase) = database.createRemoteKeyDao()

}