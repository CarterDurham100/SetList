package com.example.setlist.AddArtist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun AddArtistForm(
    name: String,
    genre: String,
    yearFormed: String,
    onNameChange: (String) -> Unit,
    onGenreChange: (String) -> Unit,
    onYearFormedChange: (String) -> Unit,
    onAddClick: () -> Unit,
    formValid: Boolean
) {
    var nameError = name.isEmpty()
    var genreError = genre.isEmpty()
    var yearError = !yearFormed.isDigitsOnly() || yearFormed.length !== 4
    //var formValid = !nameError && !genreError && !yearError


    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text("Please enter an artist.,,")
                }
            },
            label = { Text("Enter artist name..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        OutlinedTextField(
            value = genre,
            onValueChange = onGenreChange,
            isError = genreError,
            supportingText = {
                if (genreError) {
                    Text("Please enter an genre.,,")
                }
            },
            label = { Text("Enter artist's genre..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        OutlinedTextField(
            value = yearFormed,
            onValueChange = onYearFormedChange,
            isError = yearError,
            supportingText = {
                if (yearError) {
                    Text("Please enter a valid year.,,")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Enter the year they formed..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        Button(
            onClick = onAddClick,
            enabled = formValid
        ) {
            Text("Add Artist!")
        }
        HorizontalDivider(
            modifier = Modifier.padding(
                start = 2.dp,
                top = 10.dp,
                end = 2.dp,
                bottom = 10.dp
            ),
            thickness = 1.dp,
            color = Color(0xFF494d7e)
        )
    }
}