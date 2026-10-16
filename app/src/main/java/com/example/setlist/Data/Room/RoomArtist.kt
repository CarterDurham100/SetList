package com.example.setlist.Data.Room

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.time.Instant


@Entity(tableName = "artist_table")
data class RoomArtist(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "year_formed") val yearFormed: String,
    @ColumnInfo(name = "date_added") val dateAdded: Long
)