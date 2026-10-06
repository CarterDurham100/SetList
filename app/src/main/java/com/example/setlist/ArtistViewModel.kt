package com.example.setlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Data.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.setlist.Containers.ArtistApp

class ArtistViewModel(private val repository: ArtistRepository) : ViewModel() {
    val artists: StateFlow<List<Artist>> = repository.artists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addArtist(artist: Artist) {
        viewModelScope.launch { repository.addToArtistList(artist) }
    }

    fun removeArtist(artist: Artist) {
        viewModelScope.launch { repository.removeFromArtistList(artist) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ArtistApp
                ArtistViewModel(app.container.repository)
            }
        }
    }
}