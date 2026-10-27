package com.example.setlist.Data.Room

import android.content.Context
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import com.example.setlist.Artist
import com.example.setlist.Data.Room.DAOs.ArtistDAO

@Database(entities = [RoomArtist::class], version = 2, exportSchema = true)
@ColumnTypeConverters(InstantConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun artistDAO(): ArtistDAO
}