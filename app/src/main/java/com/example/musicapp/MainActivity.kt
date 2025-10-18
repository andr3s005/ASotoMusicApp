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
                // Ahora `albumId` es String (no Int)
                HomeScreen(onAlbumClick = { albumId: String ->
                    val intent = Intent().setClassName(this, "com.example.musicapp.DetailActivity")
                    intent.putExtra("albumId", albumId) // guardar string
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                } as (Int) -> Unit)
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