package com.saber.albums.repository

import com.saber.albums.data.Album
import com.saber.albums.data.Result
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun albums(): Flow<Result<List<Album>>>
}