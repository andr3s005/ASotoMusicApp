package com.example.musicapp.ui.theme.data.remote

import com.example.musicapp.ui.theme.data.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicAppService{
    @GET("api/albums")
    suspend fun getAlbums() : List<Album>

    @GET("api/albums/id")
    suspend fun getAlbumDetail(@Path("id")id: Int): Album
}
