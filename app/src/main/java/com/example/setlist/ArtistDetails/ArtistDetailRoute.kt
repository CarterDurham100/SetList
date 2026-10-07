package com.example.setlist.ArtistDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Containers.ArtistApp

@Composable
fun ArtistDetailRoute(
    artistId: Int,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    val app = LocalContext.current.applicationContext as ArtistApp
    val viewModel: ArtistDetailViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                ArtistDetailViewModel(artistId, app.container.repository)
            }
        }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistDetailScreen(
        uiState = uiState,
        onDelete = onDelete,
        onBack = onBack
    )
}