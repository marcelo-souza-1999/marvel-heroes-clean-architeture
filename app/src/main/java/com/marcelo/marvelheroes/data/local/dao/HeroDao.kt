package com.marcelo.marvelheroes.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcelo.marvelheroes.data.local.entitys.HeroesEntity

private const val NAME_TABLE = "heroes"

@Dao
interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(heroes: List<HeroesEntity>)

    @Query("SELECT * FROM $NAME_TABLE")
    fun pagingSource(): PagingSource<Int, HeroesEntity>

    @Query("DELETE FROM $NAME_TABLE")
    suspend fun deleteAll()
}