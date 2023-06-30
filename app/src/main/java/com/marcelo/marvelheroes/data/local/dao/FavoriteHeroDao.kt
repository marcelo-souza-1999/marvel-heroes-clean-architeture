package com.marcelo.marvelheroes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.marcelo.marvelheroes.data.local.entitys.FavoriteHeroEntity
import com.marcelo.marvelheroes.utils.constants.DatabaseConstants.NAME_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteHeroDao {

    @Query("SELECT * FROM $NAME_TABLE")
    suspend fun getFavorites(): List<FavoriteHeroEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favoriteHeroEntity: FavoriteHeroEntity)

    @Delete
    suspend fun deleteFavorite(favoriteHeroEntity: FavoriteHeroEntity)
}
