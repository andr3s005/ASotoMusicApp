package com.example.musicapp.ui.theme.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("cover") val cover: String? = null,
    @SerializedName("image_url") val image_url: String? = null,
    @SerializedName("description") val description: String? = null
) {
    val displayImageUrl: String?
        get() = imageUrl ?: image ?: cover ?: image_url
}
data class Track(
    val title: String,
    val artist: String,
    val trackNumber: Int
)