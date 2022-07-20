package com.saber.albums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.saber.albums.data.Album
import com.saber.albums.data.Result

import com.saber.albums.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(repository: AlbumsRepository) :
    ViewModel() {

    private val _albums = repository
        .albums()
        .asLiveData(viewModelScope.coroutineContext)

    val albums: LiveData<Result<List<Album>>>
        get() = _albums

}