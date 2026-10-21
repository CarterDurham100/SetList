package com.example.setlist.ArtistDetails

import android.widget.Button
import androidx.annotation.experimental.Experimental
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.setlist.Data.Room.InstantConverter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withZone(ZoneId.systemDefault())
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    uiState: ArtistDetailUiState,
    onDelete: () -> Unit,
    onToggleFac: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when(uiState) {
                            ArtistDetailUiState.Loading -> "Loading..."
                            is ArtistDetailUiState.NotFound -> "Artist not found :("
                            is ArtistDetailUiState.Ready -> "Im ready!"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState is ArtistDetailUiState.Ready) {
                        val isFav = uiState.artist.isFav
                        IconButton(
                            onClick = onToggleFac
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFav) "Remove from favorites" else "Add to favorites"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        when(uiState) {
            ArtistDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ArtistDetailUiState.NotFound -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No artist with id ${uiState.id}")
                }
            }
            is ArtistDetailUiState.Ready -> {
                val dateText = dateFormatter.format(uiState.artist.dateAdded)
                Column(
                    modifier = Modifier.padding(innerPadding).padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Genre: ${uiState.artist.genre}")
                    Text("Formed in: ${uiState.artist.yearFormed}")
                    Text("Record added on: $dateText")
                    Button(
                        onClick = onDelete
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}