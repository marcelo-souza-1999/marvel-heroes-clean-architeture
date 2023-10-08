package com.marcelo.marvelheroes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marcelo.marvelheroes.data.local.dao.FavoriteHeroDao
import com.marcelo.marvelheroes.data.local.entitys.FavoriteHeroEntity

@Database(entities = [FavoriteHeroEntity::class], version = 1, exportSchema = false)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun createFavoriteDao(): FavoriteHeroDao

}