package com.example.musicapp.ui.theme.data.repository

import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.data.remote.RetrofitClient


class MusicRepository(
    private val apiService: RetrofitClient = RetrofitClient
) {
    suspend fun fetchAlbums(): List<Album> {
        return apiService.service.getAlbums()
    }

    suspend fun fetchAlbumDetail(albumId: Int): Album {
        return apiService.service.getAlbumDetail(albumId)
    }
}