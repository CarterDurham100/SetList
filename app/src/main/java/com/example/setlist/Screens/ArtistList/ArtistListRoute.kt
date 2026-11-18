package com.example.setlist.Screens.ArtistList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Artist
import com.example.setlist.Containers.ArtistApp

@Composable
fun ArtistListRoute(
    onAddClick: () -> Unit,
    onArtistClick: (Artist) -> Unit,
    viewModel: ArtistListViewModel = hiltViewModel()
) {
    val app = LocalContext.current.applicationContext as ArtistApp
    //val viewModel: ArtistListViewModel = viewModel(
    //    factory = viewModelFactory {
    //        initializer { ArtistListViewModel(app.container.repository) }
    //    }
    //)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistListScreen(
        artists = uiState.artists,
        query = uiState.query,
        backupStatus = uiState.backupStatus,
        onQueryChange = viewModel::onQueryChange,
        onAddClick = onAddClick,
        onArtistClick = onArtistClick,
        onBackupClick = viewModel::onBackupClick
    )
}