package com.saber.albums.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saber.albums.data.Album


@Database(
    entities = [Album::class],
    version = 1, exportSchema = false
)
abstract class AlbumsDb : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

}