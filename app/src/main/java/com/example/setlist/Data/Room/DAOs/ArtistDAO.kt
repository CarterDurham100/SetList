package com.example.setlist.Data.Room.DAOs
import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import com.example.setlist.Artist
import com.example.setlist.Data.Room.RoomArtist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDAO {
    @Query("SELECT * FROM artists ORDER BY name DESC")
    fun observeAll():Flow<List<RoomArtist>>

    @Query("SELECT * FROM artists WHERE id = :id")
    fun observeById(id: Int): Flow<RoomArtist?>

    @Query("SELECT * FROM artists WHERE name LIKE :term ORDER BY name DESC")
    fun search(term: String): Flow<List<RoomArtist>>

    @Upsert suspend fun upsert(artist: RoomArtist)
    @Delete suspend fun delete(artist:  RoomArtist)
}