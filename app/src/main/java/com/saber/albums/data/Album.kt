package com.saber.albums.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Album(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?
)
