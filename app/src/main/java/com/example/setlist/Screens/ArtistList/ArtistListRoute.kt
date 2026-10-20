package com.example.setlist.Screens.ArtistList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Artist
import com.example.setlist.Containers.ArtistApp

@Composable
fun ArtistListRoute(
    onAddClick: () -> Unit,
    onArtistClick: (Artist) -> Unit
) {
    val app = LocalContext.current.applicationContext as ArtistApp
    val viewModel: ArtistListViewModel = viewModel(
        factory = viewModelFactory {
            initializer { ArtistListViewModel(app.container.repository) }
        }
    )
    val artists by viewModel.artistList.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val results by viewModel.results.collectAsStateWithLifecycle()

    ArtistListScreen(
        artists = results,
        query = query,
        onQueryChange = viewModel::onQueryChange,
        onAddClick = onAddClick,
        onArtistClick = onArtistClick
    )
}