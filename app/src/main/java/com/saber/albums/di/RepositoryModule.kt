package com.saber.albums.di

import com.saber.albums.api.RemoteDataSource
import com.saber.albums.db.AlbumsDb
import com.saber.albums.db.LocalDataSource
import com.saber.albums.repository.AlbumsRepository
import com.saber.albums.repository.AlbumsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ): AlbumsRepository =
        AlbumsRepositoryImpl(localDataSource, remoteDataSource, coroutineDispatcher)


    @ViewModelScoped
    @Provides
    fun provideRemoteDataSource(retrofit: Retrofit): RemoteDataSource {
        return RemoteDataSource(retrofit)
    }

    @ViewModelScoped
    @Provides
    fun provideLocalDataSource(db: AlbumsDb): LocalDataSource {
        return LocalDataSource(db.albumsDao())
    }
}