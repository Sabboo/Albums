package com.saber.albums.test


import com.saber.albums.api.RemoteDataSource
import com.saber.albums.data.Result
import com.saber.albums.db.LocalDataSource
import com.saber.albums.repository.AlbumsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.saber.albums.data.Album
import kotlinx.coroutines.flow.last


@OptIn(ExperimentalCoroutinesApi::class)
class AlbumsRepositoryImplTest {

    private lateinit var repository: AlbumsRepositoryImpl

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private val mockAlbum: Album = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository =
            AlbumsRepositoryImpl(localDataSource, remoteDataSource, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchListFromNetworkInCaseOfEmptyCache() = runTest {
        whenever(localDataSource.retrieveItems()).thenReturn(emptyList())
        whenever(remoteDataSource.fetchAlbums()).thenReturn(Result.success(emptyList()))

        val results = repository.albums().last()

        assertThat(results).isEqualTo(Result.success(emptyList<Album>()))
        verify(localDataSource, atLeastOnce()).retrieveItems()
        verify(remoteDataSource, atMost(1)).fetchAlbums()
    }

    @Test
    fun fetchListFromCacheIfExist() = runTest {
        whenever(localDataSource.retrieveItems()).thenReturn(listOf(mockAlbum))

        val results = repository.albums().last()

        assertThat(results).isEqualTo(Result.success(listOf(mockAlbum)))

        verify(localDataSource, atLeastOnce()).retrieveItems()
        verify(remoteDataSource, atMost(0)).fetchAlbums()
    }

}