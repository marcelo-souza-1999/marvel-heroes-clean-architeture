package com.marcelo.marvelheroes.data.local.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcelo.marvelheroes.utils.constants.COLUMN_INFO_ID
import com.marcelo.marvelheroes.utils.constants.COLUMN_INFO_IMAGE_URL
import com.marcelo.marvelheroes.utils.constants.COLUMN_INFO_NAME
import com.marcelo.marvelheroes.utils.constants.NAME_TABLE

@Entity(tableName = NAME_TABLE)
data class FavoriteHeroEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_INFO_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)
