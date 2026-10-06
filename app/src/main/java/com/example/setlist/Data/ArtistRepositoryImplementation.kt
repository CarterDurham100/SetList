package com.example.setlist.Data

import com.example.setlist.Artist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ArtistRepositoryImplementation : ArtistRepository {
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    override val artists: Flow<List<Artist>> = _artists

    override fun getArtistById(id: Int): Flow<Artist?> {
        return artists
            .map { list -> list.firstOrNull() { it.id == id } }
            .distinctUntilChanged()
    }

    override suspend fun addToArtistList(artist: Artist) {
        _artists.update { current -> current + artist}
    }

    override suspend fun removeFromArtistList(artist: Artist) {
        _artists.update { current -> current.filterNot { it.id == artist.id} }
    }
}