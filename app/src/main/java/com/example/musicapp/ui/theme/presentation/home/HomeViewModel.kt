package com.example.musicapp.ui.theme.presentation.home


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.data.repository.MusicRepository
import com.example.musicapp.ui.theme.presentation.common.UiState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MusicRepository = MusicRepository()
) : ViewModel() {
    private val _albumState = mutableStateOf<UiState<List<Album>>>(UiState.Loading)
    val albumState: State<UiState<List<Album>>> = _albumState

    private val _isPlaying = mutableStateOf(false)
    val isPlaying: State<Boolean> = _isPlaying

    private val _miniPlayerAlbum = mutableStateOf<Album?>(null)
    val miniPlayerAlbum : State<Album?> = _miniPlayerAlbum

    init {
        fetchAlbums()
    }

    private fun fetchAlbums(){
        viewModelScope.launch{
            _albumState.value = UiState.Loading
            try {
                val albums = repository.fetchAlbums()
                _albumState.value = UiState.Success(albums)
                _miniPlayerAlbum.value = albums.firstOrNull()
            } catch (e: Exception) {
                _albumState.value = UiState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun tooglePlayPause(){
        _isPlaying.value = !_isPlaying.value
    }

}