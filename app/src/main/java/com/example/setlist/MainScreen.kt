package com.example.setlist

import android.R.attr.title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.setlist.ui.theme.SetListTheme

data class Artist(var id: Int = 0, var name: String, val genre: String, val yearFormed: String)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var name by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var yearFormed by remember { mutableStateOf("") }
    val artistList = remember { mutableStateListOf<Artist>() }

    val formValid by remember {
        derivedStateOf {
            name.isNotEmpty() && genre.isNotEmpty() && yearFormed.length == 4 && yearFormed.isDigitsOnly()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {Text("The Artist App.")})
        }
    ) { scafPadding ->
        Column(
            modifier = Modifier
                .padding(scafPadding)
                .fillMaxSize()
        ) {
            AddArtistForm(
                name = name,
                genre = genre,
                yearFormed = yearFormed,
                onNameChange = { name = it },
                onGenreChange = { genre = it },
                onYearFormedChange = { yearFormed = it },
                onAddClick = {
                    artistList.add(Artist(name, genre, yearFormed))
                    name = ""
                    genre = ""
                    yearFormed = ""
                },
                formValid = formValid
            )
            DisplayArtistList(
                artistList = artistList,
                onRemoveClick =  { artistList.remove(it) }
            )
        }
    }
}

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

@Composable
fun DisplayArtistList(
    artistList: List<Artist>,
    onRemoveClick: (Artist) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(artistList) { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column() {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = item.genre,
                        fontStyle = FontStyle.Italic
                    )
                    //if (item.yearFormed == 0) {
                    //    Text(
                    //        text = "Founded in: N/A",
                    //        fontStyle = FontStyle.Italic
                    //    )
                    //} else {
                        Text(
                            text = "Founded in: " + item.yearFormed,
                            fontStyle = FontStyle.Italic
                        )
                    //}
                }

                FloatingActionButton(
                    onClick = {
                        onRemoveClick(item)
                    }
                ) {
                    Text("kill")
                }
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
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SetListTheme() {
        MainScreen()
    }
}