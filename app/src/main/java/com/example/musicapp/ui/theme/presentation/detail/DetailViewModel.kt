package com.example.musicapp.ui.theme.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.data.repository.MusicRepository
import com.example.musicapp.ui.theme.presentation.common.UiState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MusicRepository = MusicRepository()
) : ViewModel() {
    private val _detailAlbumState = mutableStateOf<UiState<Album>>(UiState.Loading)
    val detailAlbumState : State<UiState<Album>> = _detailAlbumState

    fun fetchAlbumDetail(albumId: String?) {
        viewModelScope.launch {
            // Solo cargamos si no se ha cargado previamente (o si es otro ID)
//            if (_detailAlbumState.value !is UiState.Success) {
//                _detailAlbumState.value = UiState.Loading
                try {
                    val album = repository.fetchAlbumDetail(albumId)
                    _detailAlbumState.value = UiState.Success(album)
                } catch (e: Exception) {
                    _detailAlbumState.value = UiState.Error("Error al cargar el detalle: ${e.message}")
                }
            }
        }
    }
//}