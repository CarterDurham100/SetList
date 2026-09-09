package com.example.setlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.setlist.ui.theme.SetListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SetListTheme {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    AddArtistForm()
                    DisplayArtistList()
                }
            }
        }
    }
}

data class Artist(var name: String, val genre: String, val yearFormed: Int?)
var artistList = mutableStateListOf<Artist>()
@Composable
fun AddArtistForm() {
    var name by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var yearFormed by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter artist name..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        OutlinedTextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Enter artist's genre..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        OutlinedTextField(
            value = yearFormed,
            onValueChange = { yearFormed = it },
            label = { Text("Enter the year they formed..") },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
        )
        FloatingActionButton(
            onClick = {
                val artist = Artist(
                    name = name,
                    yearFormed = yearFormed.toIntOrNull() ?: 0,
                    genre = genre
                )
                artistList.add(artist)
                name = ""
                genre = ""
                yearFormed = ""
            }
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
fun DisplayArtistList() {
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
                        artistList.remove(item)
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