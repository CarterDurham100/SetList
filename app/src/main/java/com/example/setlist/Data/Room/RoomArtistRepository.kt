package com.example.setlist.Data.Room

import android.provider.MediaStore
import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.Room.DAOs.ArtistDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomArtistRepository(private  val dao: ArtistDAO): ArtistRepository {

}