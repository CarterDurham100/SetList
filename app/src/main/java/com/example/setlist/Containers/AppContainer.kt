package com.example.setlist.Containers

import android.content.Context
import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.ArtistRepositoryImplementation
import com.example.setlist.Data.Room.AppDatabase
import com.example.setlist.Data.Room.RoomArtistRepository

class AppContainer(context: Context) {

    val repository: ArtistRepository = RoomArtistRepository(AppDatabase.get(context).artistDAO())
}