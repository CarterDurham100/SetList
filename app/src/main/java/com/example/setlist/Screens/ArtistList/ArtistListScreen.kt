package com.example.setlist.Screens.ArtistList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.setlist.Artist
import com.example.setlist.MainScreen
import com.example.setlist.ui.theme.SetListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListScreen(
    artists: List<Artist>,
    onRemove: (Artist) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artist List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (artists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Empty list...")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(artists, key = { it.id }) { artist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(artist.name) },
                            supportingContent = {
                                Text("${artist.genre}, formed in: ${artist.yearFormed}")
                            },
                            trailingContent = {
                                IconButton(onClick = { onRemove(artist) }) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Remove ${artist.name}"
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SetListTheme() {
        ArtistListScreen(artists = emptyList(), onBack = {}, onRemove = {})
    }
}