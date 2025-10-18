package com.example.musicapp.ui.theme.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp.ui.theme.PrimaryDark
import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.presentation.common.UiState
import com.example.musicapp.ui.theme.Purple80
import com.example.musicapp.ui.theme.PurpleGrey40
import com.example.musicapp.ui.theme.SecondaryLight

@Composable
fun HomeScreen(
    onAlbumClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
){
    val albumState = viewModel.albumState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F5))
    ) {
        HomeHeader()

        Spacer(modifier = Modifier.height(16.dp))

        when(albumState){
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = PrimaryDark)
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        albumState.message,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
            is UiState.Success -> {
                Text(
                    "Albums",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(albumState.data) { album ->
                        AlbumCard(album = album, onClick = onAlbumClick)
                    }
                }

                Spacer(modifier = Modifier
                    .height(24.dp))

                Text(
                    "Recently Played",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(albumState.data.take(5)) { album ->
                        RecentlyPlayedItem(album = album, onClick = onAlbumClick)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PrimaryDark.copy(alpha = 0.9f), SecondaryLight.copy(alpha = 0.5f)),
                    startY = 0f,
                    endY = 500f
                ),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .padding(24.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column {
            Text(
                "Bienvenido de vuelta",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
            Text(
                "Andres Soto",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Ajustes",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
        )
    }
}

@Composable
fun AlbumCard(
    album : Album, onClick: (Int) -> Unit
){
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(width = 200.dp, height = 280.dp)
            .clickable { onClick(album.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = "Carátula de ${album.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 400f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    album.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1
                )
                Text(
                    album.artist,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }

            Button(
                onClick = { onClick(album.id) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(48.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reproducir",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(album: Album, onClick: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick(album.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen pequeña con Coil
            AsyncImage(
                model = album.imageUrl,
                contentDescription = "Carátula de ${album.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Título y Artista
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    album.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    album.artist,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }

            // Botón de Play
            IconButton(onClick = { onClick(album.id) }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.Blue
                )
            }
        }
    }
}