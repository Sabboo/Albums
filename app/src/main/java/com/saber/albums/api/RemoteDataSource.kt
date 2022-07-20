package com.saber.albums.api

import com.saber.albums.data.Album
import com.saber.albums.data.Result
import com.saber.albums.utils.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun fetchAlbums(): Result<List<Album>> {
        val albumService = retrofit.create(AlbumsService::class.java)
        return getResponse(
            request = { albumService.getAlbums() },
            defaultErrorMessage = "Error fetching Albums list"
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(
                    errorResponse?.message ?: defaultErrorMessage, errorResponse
                )
            }
        } catch (e: UnknownHostException) {
            Result.error("No Connection", null)
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}