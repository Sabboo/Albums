package com.saber.albums.repository

import androidx.annotation.WorkerThread
import com.saber.albums.api.RemoteDataSource
import com.saber.albums.data.Album
import com.saber.albums.data.Result
import com.saber.albums.db.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class AlbumsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AlbumsRepository {

    @WorkerThread
    override fun albums(): Flow<Result<List<Album>>> {
        return flow {
            val cacheResult = fetchCached()
            if (cacheResult.status == Result.Status.SUCCESS)
                emit(cacheResult)
            else {
                emit(Result.loading())
                val remoteResult = fetchRemote()
                if (remoteResult.status == Result.Status.SUCCESS)
                    emit(Result.success(localDataSource.retrieveItems()))
                else
                    emit(remoteResult)
            }
        }.flowOn(ioDispatcher)
    }

    private fun fetchCached(): Result<List<Album>> {
        val result = localDataSource.retrieveItems()
        return if (result.isEmpty().not())
            Result.success(result)
        else
            Result.error("No Cache", null)
    }

    private suspend fun fetchRemote(): Result<List<Album>> {
        val remoteResult = remoteDataSource.fetchAlbums()

        if (remoteResult.status == Result.Status.SUCCESS) {
            remoteResult.data?.let { it ->
                localDataSource.insert(it)
            }
        }
        return remoteResult
    }

}
