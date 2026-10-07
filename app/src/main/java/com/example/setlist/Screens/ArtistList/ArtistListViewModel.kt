package com.example.setlist.Screens.ArtistList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.setlist.Artist
import com.example.setlist.Data.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArtistListViewModel(private val repository: ArtistRepository) : ViewModel() {
    val artistList: StateFlow<List<Artist>> = repository.artists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun remove(artist: Artist) = viewModelScope.launch { repository.removeFromArtistList(artist) }
}