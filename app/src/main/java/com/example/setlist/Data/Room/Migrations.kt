package com.example.setlist.Data.Room

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE artist_table ADD COLUMN is_fav INTEGER NOT NULL DEFAULT 0")
    }
}