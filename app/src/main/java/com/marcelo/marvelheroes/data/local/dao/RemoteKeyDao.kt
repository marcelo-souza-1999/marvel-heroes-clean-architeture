package com.marcelo.marvelheroes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcelo.marvelheroes.data.local.entitys.RemoteKeyEntity

private const val NAME_TABLE = "remote"

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeyEntity: RemoteKeyEntity)

    @Query("SELECT * FROM $NAME_TABLE")
    suspend fun getRemoteKey(): RemoteKeyEntity

    @Query("DELETE FROM $NAME_TABLE")
    suspend fun deleteALl()
}