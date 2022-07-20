package com.saber.albums.db

import com.saber.albums.data.Album

class LocalDataSource(private val dao: AlbumsDao) {
    fun insert(items: List<Album>) {
        dao.insertAll(items)
    }

    fun retrieveItems(): List<Album> {
        return dao.getAlbums()
    }
}