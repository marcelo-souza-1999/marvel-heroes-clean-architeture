package com.marcelo.marvelheroes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marcelo.marvelheroes.data.local.dao.FavoriteHeroDao
import com.marcelo.marvelheroes.data.local.dao.HeroDao
import com.marcelo.marvelheroes.data.local.dao.RemoteKeyDao
import com.marcelo.marvelheroes.data.local.entitys.FavoriteHeroEntity
import com.marcelo.marvelheroes.data.local.entitys.HeroesEntity
import com.marcelo.marvelheroes.data.local.entitys.RemoteKeyEntity

@Database(
    entities = [FavoriteHeroEntity::class, HeroesEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun createFavoriteDao(): FavoriteHeroDao
    abstract fun createHeroDao(): HeroDao
    abstract fun createRemoteKeyDao(): RemoteKeyDao

}