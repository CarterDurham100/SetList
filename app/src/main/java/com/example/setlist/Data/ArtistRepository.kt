package com.example.setlist.Data

import com.example.setlist.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    val artists: Flow<List<Artist>>
    fun getArtistById(id: Int): Flow<Artist?>
    suspend fun addToArtistList(artist: Artist)
    suspend fun removeFromArtistList(artist: Artist)
}