package com.example.setlist
import java.time.Instant

data class Artist(
    var id: Int,
    val name: String,
    val genre: String,
    val yearFormed: String,
    val isFav: Boolean = false,
    val dateAdded: Instant
)