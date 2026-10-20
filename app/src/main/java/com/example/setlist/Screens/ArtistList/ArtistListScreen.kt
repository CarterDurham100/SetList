package com.example.setlist.Screens.ArtistList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room3.Query
import androidx.room3.util.TableInfo
import com.example.setlist.Artist
import com.example.setlist.ui.theme.SetListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListScreen(
    artists: List<Artist>,
    query: String,
    onQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onArtistClick: (Artist) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Artist List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) { Text("+") }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Search artists...")},
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if(query.isNotEmpty()) {
                        IconButton(
                            onClick = {onQueryChange("")}
                        ) {
                            Icon(Icons.Filled.Clear, contentDescription = "")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        if (artists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Empty list...")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                items(artists, key = { it.id }) { artist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable { onArtistClick(artist) }
                    ) {
                        ListItem(
                            headlineContent = { Text(artist.name) },
                            supportingContent = {
                                Text("${artist.genre}, formed in: ${artist.yearFormed}")
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
fun ArtistListScreenPreview() {
    SetListTheme {
        ArtistListScreen(artists = emptyList(), query = "hi", onQueryChange = {}, onAddClick = {}, onArtistClick = {})
    }
}