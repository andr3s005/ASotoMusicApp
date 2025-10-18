package com.example.musicapp.ui.theme.data.repository

import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.data.remote.RetrofitClient
import retrofit2.HttpException


class MusicRepository(
    private val apiService: RetrofitClient = RetrofitClient
) {
    suspend fun fetchAlbums(): List<Album> {
        return apiService.service.getAlbums()
    }

    suspend fun fetchAlbumDetail(albumId: String?): Album {
        val id = albumId?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("albumId es nulo o vacío")

        try {
            return apiService.service.getAlbumDetail(id)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                throw HttpException(e.response()!!) // propagar o manejar según necesidad
            }
            throw e
        }
    }
}