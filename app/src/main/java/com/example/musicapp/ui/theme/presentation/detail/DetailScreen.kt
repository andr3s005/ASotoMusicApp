package com.example.musicapp.ui.theme.presentation.detail

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp.ui.theme.PrimaryDark
import com.example.musicapp.ui.theme.data.model.Album
import com.example.musicapp.ui.theme.data.model.Track
import com.example.musicapp.ui.theme.presentation.common.UiState

@Composable
fun DetailScreen(
    albumId: Int,
    viewModel: DetailViewModel = viewModel()
) {
    val detailState = viewModel.detailAlbumState.value

    // Ejecuta la llamada a la API al iniciar la pantalla con el ID recibido
    LaunchedEffect(albumId) {
        viewModel.fetchAlbumDetail(albumId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (detailState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryDark)
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Text(detailState.message, color = Color.Red, modifier = Modifier.padding(16.dp))
                }
            }
            is UiState.Success -> {
                val album = detailState.data

                // 1. Header de Detalle (Imagen grande, Scrim, Título, Acciones)
                DetailHeader(album = album)

                // 2. Contenido Principal (Scrollable)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Ocupa el espacio restante
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Card "About this album"
                    item {
                        DetailAboutCard(album)
                    }

                    // Chip/Etiqueta
                    item {
                        AssistChip(
                            onClick = { /* Acción ficticia */ },
                            label = { Text("Artist: ${album.artist}", fontWeight = FontWeight.SemiBold) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Artista",
                                    Modifier.size(AssistChipDefaults.IconSize)
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = PrimaryDark.copy(alpha = 0.1f),
                                labelColor = PrimaryDark
                            )
                        )
                    }

                    // Título de la lista de canciones
                    item {
                        Text(
                            "Track List (10 canciones ficticias)",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 3. LazyColumn con 10 canciones ficticias
                    items(10) { index ->
                        val track = Track(
                            title = "${album.title} • Track ${index + 1}",
                            artist = album.artist,
                            trackNumber = index + 1
                        )
                        TrackListItem(album = album, track = track)
                    }
                }
            }
        }
    }
}


@Composable
fun DetailHeader(album : Album){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ){
        AsyncImage(
            model = album.imageUrl,
            contentDescription = "Caratula del album: ${album.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            PrimaryDark.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                album.title,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                album.artist,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Play", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Play", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        Icons.Filled.Shuffle,
                        contentDescription = "Aleatorio",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Aleatorio")
                }
            }
        }
    }
}

@Composable
fun DetailAboutCard(album: Album) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "About this album",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                album.description ?: "No se encontró una descripción para este álbum.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TrackListItem(album: Album, track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { /* Tocar canción */ }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del álbum (misma imagen para todas las canciones)
        AsyncImage(
            model = album.imageUrl,
            contentDescription = "Carátula de ${album.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Título y Artista
        Column(modifier = Modifier.weight(1f)) {
            Text(
                track.title,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                maxLines = 1
            )
            Text(
                track.artist,
                color = Color.Gray,
                fontSize = 13.sp,
                maxLines = 1
            )
        }

        // Botón de opciones
        IconButton(onClick = { /* Más opciones */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Opciones",
                tint = Color.Gray
            )
        }
    }
}