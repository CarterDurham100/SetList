package com.example.setlist.ArtistDetails

import com.example.setlist.Artist

sealed interface ArtistDetailUiState {
    data object Loading : ArtistDetailUiState
    data class NotFound(val id: Int) : ArtistDetailUiState
    data class Ready(val artist: Artist, val isOnList: Boolean) : ArtistDetailUiState
}