package com.example.musicapp.ui.theme.data.remote

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.musicapp.ui.theme.data.remote.MusicAppService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
object RetrofitClient {
    // Logging de cuerpo para ver request/response
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor que imprime la URL exacta solicitada
    private val urlLogger = Interceptor { chain ->
        val request = chain.request()
        Log.d("RetrofitURL", "Request -> ${request.method} ${request.url}")
        chain.proceed(request)
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(urlLogger)
        .addInterceptor(logging)
        .build()

    // Usar solo el host (o termina en /api/ si prefieres usar @GET("albums/..."))
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://music.juanfrausto.com/api/")
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: MusicAppService = retrofit.create(MusicAppService::class.java)
}