package com.example.setlist.Screens.ArtistList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.AddArtist.AddArtistViewModel
import com.example.setlist.ArtistViewModel
import com.example.setlist.Containers.ArtistApp


val AddArtistViewModelFactory = viewModelFactory {
    initializer {
        val app = this[APPLICATION_KEY] as ArtistApp
        ArtistListViewModel(app.container.repository)
    }
}

@Composable
fun ArtistListRoute(
    onBack: () -> Unit,
    viewModel: ArtistViewModel = viewModel(factory = AddArtistViewModelFactory)
) {
    val artists by viewModel.artists.collectAsStateWithLifecycle()

    ArtistListScreen(
        artists = artists,
        onRemove = viewModel::removeArtist,
        onBack = onBack
    )
}