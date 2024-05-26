package com.marcelo.marvelheroes.data.local.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcelo.marvelheroes.utils.constants.COLUMN_INFO_NEXT_KEY

private const val NAME_TABLE = "remote"

@Entity(tableName = NAME_TABLE)
data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = COLUMN_INFO_NEXT_KEY)
    val nextKey: Int?
)
