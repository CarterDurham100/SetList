package com.example.setlist.ArtistDetails

import androidx.compose.ui.text.style.TextDecoration.Companion.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.setlist.Containers.ArtistApp
import com.example.setlist.Data.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY


class ArtistDetailViewModel(private val artistId: Int, private val repository: ArtistRepository) : ViewModel() {
    val uiState: StateFlow<ArtistDetailUiState> =
        repository.artists
            .map { artists ->
                val artist = artists.firstOrNull { it.id == artistId }
                if (artist == null) {
                    ArtistDetailUiState.NotFound(artistId)
                } else {
                    ArtistDetailUiState.Ready(artist, isOnList = true)
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ArtistDetailUiState.Loading)

    fun deleteArtist() {
        viewModelScope.launch {
            val artist = repository.getArtistById(artistId).first() ?: return@launch
            repository.removeFromArtistList(artist)
        }
    }

    fun toggleArtistList() = viewModelScope.launch {
        val current = uiState.value as? ArtistDetailUiState.Ready ?: return@launch
        if(current.isOnList) {
            repository.removeFromArtistList(current.artist)
        } else {
            repository.addToArtistList(current.artist)
        }
    }

    companion object {
        fun factory(id: Int, repository: ArtistRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { ArtistDetailViewModel(id, repository) }
        }
    }
}