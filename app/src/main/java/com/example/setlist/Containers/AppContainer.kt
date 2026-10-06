package com.example.setlist.Containers

import android.content.Context
import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.ArtistRepositoryImplementation

class AppContainer(context: Context) {
    val repository: ArtistRepository = ArtistRepositoryImplementation()
}