package com.example.musicapp.ui.theme.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Album(
    val id: Int,
    val title : String,
    val artist : String,
    @SerializedName("image_url")
    val imageUrl : String,
    val description : String? = null
): Serializable

data class Track(
    val title: String,
    val artist: String,
    val trackNumber: Int
)