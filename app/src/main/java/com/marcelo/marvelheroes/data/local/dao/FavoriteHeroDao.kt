package com.marcelo.marvelheroes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.marcelo.marvelheroes.data.local.entitys.FavoriteHeroEntity
import kotlinx.coroutines.flow.Flow

private const val NAME_TABLE = "favorites"

@Dao
interface FavoriteHeroDao {
    @Query("SELECT * FROM $NAME_TABLE")
    fun getFavorites(): Flow<List<FavoriteHeroEntity>>

    @Query("SELECT * FROM $NAME_TABLE WHERE id = :idHero")
    suspend fun hasFavorite(idHero: Int): FavoriteHeroEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favoriteHeroEntity: FavoriteHeroEntity)

    @Query("DELETE FROM $NAME_TABLE WHERE id = :idHero")
    suspend fun deleteFavorite(idHero: Int)
}
