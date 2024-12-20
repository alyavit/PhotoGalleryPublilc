package com.example.photogallery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    val url: String?,
    val author: String?,
    val favorite: Boolean
)