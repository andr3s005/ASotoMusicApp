package com.example.musicapp.ui.theme.data.repository

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.AlbumListResponse
import com.example.musicapp.data.remote.RetrofitClient


class MusicRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getAlbums(): AlbumListResponse {
        return apiService.getAlbums()
    }

    suspend fun getAlbumDetail(albumId: String): Album {
        return apiService.getAlbumDetail(albumId)
    }
}