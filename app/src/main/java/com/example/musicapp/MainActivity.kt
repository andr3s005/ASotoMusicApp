package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.presentation.home.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {
                // Llamar a HomeScreen; onAlbumClick intenta abrir DetailActivity por nombre
                HomeScreen(onAlbumClick = { albumId ->
                    val intent = Intent().setClassName(this, "com.example.musicapp.DetailActivity")
                    intent.putExtra("albumId", albumId)
                    // Sólo iniciar si existe una Activity que pueda manejar el Intent
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    } else {
                        // opcional: manejar el caso donde no exista la Activity (log, Toast, etc.)
                    }
                })
            }
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text("Hola, $name")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MusicAppTheme {
        Greeting("Android")
    }
}