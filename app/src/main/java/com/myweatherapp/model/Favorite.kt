package com.myweatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "fav_tbl")
data class Favorite(
    @PrimaryKey
    val id: Int = 0,
    val city: String,
    val country: String
)
