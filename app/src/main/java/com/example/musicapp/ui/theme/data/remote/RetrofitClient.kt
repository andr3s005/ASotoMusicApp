package com.example.musicapp.ui.theme.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.musicapp.ui.theme.data.remote.MusicAppService

object RetrofitClient {
    private const val BASE_URL = "https://music.juanfrausto.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: MusicAppService by lazy {
        retrofit.create(MusicAppService::class.java)
    }
}