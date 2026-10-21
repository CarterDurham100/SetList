package com.example.setlist.Data.Room

import android.provider.MediaStore
import androidx.room3.Room
import androidx.room3.util.getTotalChangedRows
import com.example.setlist.Artist
import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.Room.DAOs.ArtistDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomArtistRepository(private  val dao: ArtistDAO): ArtistRepository {
    override val artists: Flow<List<Artist>> = dao.observeAll().map { rows -> rows.map { it.toArtist() } }
    override fun getArtistById(id: Int): Flow<Artist?> = dao.observeById(id).map { it?.toArtist() }
    override fun search(term: String): Flow<List<Artist>> = dao.search(term).map { rows -> rows.map {it.toArtist()}}
    override suspend fun addToArtistList(artist: Artist) = dao.upsert(artist.toEntity())
    override suspend fun removeFromArtistList(artist: Artist) = dao.delete(artist.toEntity())
}

private fun Artist.toEntity(): RoomArtist = RoomArtist(id = id, name = name, genre = genre, yearFormed = yearFormed, isFav = isFav, dateAdded = dateAdded)
private fun RoomArtist.toArtist(): Artist = Artist(id = id, name = name, genre = genre, yearFormed = yearFormed, isFav = isFav, dateAdded = dateAdded)