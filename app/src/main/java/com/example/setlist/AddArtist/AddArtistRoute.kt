package com.example.setlist.AddArtist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Artist
import com.example.setlist.Containers.ArtistApp

@Composable
fun AddArtistRoute(
    onAddClick: () -> Unit
) {
    val app = LocalContext.current.applicationContext as ArtistApp
    val viewModel: AddArtistViewModel = viewModel(
        factory = viewModelFactory {
            initializer { AddArtistViewModel(app.container.repository) }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        AddArtistForm(
            name = viewModel.name,
            onNameChange = viewModel::onNameChange,
            genre = viewModel.genre,
            onGenreChange = viewModel::onGenreChange,
            yearFormed = viewModel.yearFormed,
            onYearFormedChange = viewModel::onYearFormedChange,
            onAddClick = {
                viewModel.add(
                    Artist(
                        id = 0,
                        name = viewModel.name,
                        genre = viewModel.genre,
                        yearFormed = viewModel.yearFormed
                    )
                )
                onAddClick()
            },
            formValid = viewModel.name.isNotEmpty() &&
                    viewModel.genre.isNotEmpty() &&
                    viewModel.yearFormed.length == 4 &&
                    viewModel.yearFormed.all { it.isDigit() }
        )
    }
}