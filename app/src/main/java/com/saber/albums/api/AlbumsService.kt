package com.saber.albums.api

import com.saber.albums.data.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumsService {

    @GET("albums")
    suspend fun getAlbums(): Response<List<Album>>

}