package com.marcelo.marvelheroes.di.modules

import android.content.Context
import androidx.room.Room
import com.marcelo.marvelheroes.data.local.dao.FavoriteHeroDao
import com.marcelo.marvelheroes.data.local.database.MarvelDatabase
import com.marcelo.marvelheroes.utils.constants.DATABASE_NAME
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Module
@Single
class DatabaseModule : KoinComponent {

    private val context: Context by inject()

    @Single
    fun providesDatabase(): MarvelDatabase {
        return Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Single
    fun providesFavoriteDao(database: MarvelDatabase): FavoriteHeroDao {
        return database.createFavoriteDao()
    }
}