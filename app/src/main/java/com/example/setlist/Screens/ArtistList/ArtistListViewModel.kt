package com.example.setlist.Screens.ArtistList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Artist
import com.example.setlist.Containers.ArtistApp
import com.example.setlist.Data.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY


class ArtistListViewModel(private val repository: ArtistRepository) : ViewModel() {
    val artistList: StateFlow<List<Artist>> = repository.artists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    companion object {
        fun Factory(repository: ArtistRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { ArtistListViewModel(repository) }
        }
    }
}