package com.example.setlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.setlist.ui.theme.SetListTheme

data class Artist(var name: String, val genre: String, val yearFormed: Int?)
@Composable
fun MainScreen() {
    var name by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var yearFormed by remember { mutableStateOf("") }
    val artistList = remember { mutableStateListOf<Artist>() }
    Column() {
        AddArtistForm(
            name = name,
            genre = genre,
            yearFormed = yearFormed,
            onNameChange = { name = it },
            onGenreChange = { genre = it },
            onYearFormedChange = { yearFormed = it },
            onAddClick = {
                artistList.add(Artist(name, genre, yearFormed.toIntOrNull() ?: 0))
            }
        )
        DisplayArtistList(
            artistList = artistList,
            onRemoveClick =  { artistList.remove(it) }
        )
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
    onAddClick: () -> Unit
    ) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
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
            label = { Text("Enter the year they formed..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        FloatingActionButton(
            onClick = onAddClick
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
                    if (item.yearFormed == 0) {
                        Text(
                            text = "Founded in: N/A",
                            fontStyle = FontStyle.Italic
                        )
                    } else {
                        Text(
                            text = "Founded in: " + item.yearFormed,
                            fontStyle = FontStyle.Italic
                        )
                    }
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