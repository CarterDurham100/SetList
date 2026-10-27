package com.example.setlist.Data.Hilt

import com.example.setlist.Data.ArtistRepository
import com.example.setlist.Data.Room.RoomArtistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindArtistRepository(impl: RoomArtistRepository): ArtistRepository
}