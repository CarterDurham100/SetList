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
import androidx.compose.material.icons.filled.Refresh
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
import com.example.setlist.Artist
import com.example.setlist.Data.BackupStatus
import com.example.setlist.ui.theme.SetListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListScreen(
    artists: List<Artist>,
    query: String,
    backupStatus: BackupStatus,
    onQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onArtistClick: (Artist) -> Unit,
    onBackupClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artist List") },
                actions = {
                    IconButton(
                        onClick = onBackupClick,
                        enabled = backupStatus !is BackupStatus.Running
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Back up now")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) { Text("+") }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val statusText = when (backupStatus) {
                BackupStatus.Idle -> null
                BackupStatus.Running -> "Backing up..."
                is BackupStatus.Finished -> "Last backup: ${backupStatus.numOfArtistsBackedUp} artists"
                BackupStatus.Failed -> "Backup failed"
            }
            if (statusText != null) {
                Text(
                    text = statusText,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Search artists...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (artists.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Empty list...")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
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
}

@Preview(showBackground = true)
@Composable
fun ArtistListScreenPreview() {
    SetListTheme {
        ArtistListScreen(
            artists = emptyList(),
            query = "hi",
            backupStatus = BackupStatus.Finished(4),
            onQueryChange = {},
            onAddClick = {},
            onArtistClick = {},
            onBackupClick = {}
        )
    }
}