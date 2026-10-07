package com.example.setlist.AddArtist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Artist
import com.example.setlist.ArtistDetails.ArtistDetailViewModel
import com.example.setlist.Data.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddArtistViewModel(private val repository: ArtistRepository) : ViewModel() {
    var name by mutableStateOf("")
        private set

    var genre by mutableStateOf("")
        private set

    var yearFormed by mutableStateOf("")
        private set

    fun onNameChange(value: String) {
        name = value
    }

    fun onGenreChange(value: String) {
        genre = value
    }

    fun onYearFormedChange(value: String) {
        yearFormed = value
    }

    val artistList: StateFlow<List<Artist>> = repository.artists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun add(artist: Artist) = viewModelScope.launch { repository.addToArtistList(artist) }

    fun factory(id: Int, repository: ArtistRepository): ViewModelProvider.Factory = viewModelFactory {
        initializer { ArtistDetailViewModel(id, repository) }
    }
}