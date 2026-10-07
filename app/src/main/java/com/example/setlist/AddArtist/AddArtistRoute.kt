package com.example.setlist.AddArtist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.setlist.Artist


@Composable
fun AddArtistRoute(
    onAddClick: () -> Unit,
    viewModel: AddArtistViewModel = viewModel()
) {
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
                    viewModel.yearFormed.isDigitsOnly()
        )
    }
}