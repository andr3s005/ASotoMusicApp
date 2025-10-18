package com.example.musicapp.ui.theme.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicapp.ui.theme.MusicAppTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val albumId = intent?.getStringExtra("albumId")
        if (albumId.isNullOrBlank()) {
            finish() // evita crash si no llega id
            return
        }

        setContent {
            MusicAppTheme {
                DetailScreen(albumId = albumId,)
            }
        }
    }
}