package com.example.musicapp.ui.theme.presentation.home

import android.R.attr.onClick
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.PrimaryDark
import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.presentation.common.UiState
import com.example.musicapp.ui.theme.SecondaryLight
import com.example.musicapp.ui.theme.presentation.common.MiniPlayer

@Composable
fun HomeScreen(
    onAlbumClick: (String) -> Unit,
    userName : String = "Andres Soto",
    viewModel: HomeViewModel = viewModel()
){
    val albumState = viewModel.albumState

    HomeScreenContent(
        albumState = albumState.value,
        onAlbumClick = onAlbumClick,
        userName = userName
    )
}

@Composable
fun HomeScreenContent(
    albumState: UiState<List<Album>>,
    onAlbumClick: (String) -> Unit,
    userName: String = "Andres Soto"
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F3FB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeHeader(userName = userName)

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
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Albums", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 16.dp))
                            Text("See more", color = PrimaryDark, modifier = Modifier.padding(end = 16.dp))
                        }
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

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Recently Played", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 16.dp))
                            Text("See more", color = PrimaryDark, modifier = Modifier.padding(end = 16.dp))
                        }

                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(bottom = 80.dp)
                        ) {
                            items(albumState.data.take(6)) { album ->
                                RecentlyPlayedItem(album = album, onClick = onAlbumClick)
                            }
                        }
                    }
                }
            }
        }

        // Mini player fijo abajo (ahora dentro del Box, por eso .align funciona)
        MiniPlayer(
            album = (if (albumState is UiState.Success && albumState.data.isNotEmpty()) albumState.data.first() else null),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClickPlay = { it?.let { album -> onAlbumClick(album.id) } }
        )
    }
}
@Composable
fun HomeHeader(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Black),
                )
            )
            .padding(20.dp)
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White, modifier = Modifier.align(Alignment.TopStart))
        Icon(imageVector = Icons.Default.Search, contentDescription = "Ajustes", tint = Color.White, modifier = Modifier.align(Alignment.TopEnd))
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(text = "Good Morning!", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
            Text(text = userName, color = Color.White, fontSize = 28.sp, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun AlbumCard(album: Album, onClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(width = 200.dp, height = 260.dp)
            .clickable { onClick(album.id) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = album.displayImageUrl,
                contentDescription = album.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // degradado inferior
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xAA000000)
                        ), startY = 200f
                    )
                ))
            // texto inferior
            Column(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)) {
                Text(album.title, color = Color.White, style = MaterialTheme.typography.titleSmall)
                Text(album.artist, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
            }
            // boton circular play
            IconButton(
                onClick = { onClick(album.id) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(PrimaryDark)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(album: Album, onClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick(album.id) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = album.imageUrl,
                contentDescription = album.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(album.title, style = MaterialTheme.typography.bodyMedium)
                Text("${album.artist} • Popular Song", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            IconButton(onClick = { /* abrir more menu */ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun MiniPlayer(album: Album?, modifier: Modifier = Modifier, onClickPlay: (Album?) -> Unit) {
    if (album == null) return
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryDark)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = album.imageUrl, contentDescription = album.title, modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(album.title, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    Text(album.artist, color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.bodySmall)
                }
            }
            IconButton(onClick = { onClickPlay(album) }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleAlbums = listOf(
        Album("1", "Album One", "Artist A", "https://via.placeholder.com/150"),
        Album("2", "Album Two", "Artist B", "https://via.placeholder.com/150"),
        Album("3", "Album Three", "Artist C", "https://via.placeholder.com/150")
    )
    MusicAppTheme {
        HomeScreenContent(albumState=UiState.Success(sampleAlbums), onAlbumClick = { _: String -> },)
    }
}