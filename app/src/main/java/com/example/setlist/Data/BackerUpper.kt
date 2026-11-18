package com.example.setlist.Data
import android.content.Context
import com.example.setlist.Artist
import kotlinx.serialization.Serializable;
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Instant

@Serializable
data class BackupArtist(
    val name: String,
    val genre: String,
    val yearFormed: String,
    val isFav: Boolean = false,
    val dateAdded: Long
)

@Serializable
data class BackupFile(
    val timeCreatedAt: Long,
    val artists: List<BackupArtist>
)

fun Artist.toBackupArtist() = BackupArtist(
    name = name,
    genre = genre,
    yearFormed = yearFormed,
    isFav = isFav,
    dateAdded = dateAdded.toEpochMilli()
)

fun List<Artist>.toBackupFile(timeCreatedAt: Long = System.currentTimeMillis()): BackupFile =
    BackupFile(
        timeCreatedAt = timeCreatedAt,
        artists = map { it.toBackupArtist() }
    )

fun backupName(timeCreatedAt: Long): String = "setback_list_$timeCreatedAt.json"

fun writeBackup(context: Context, artists: List<Artist>): File {
    val backup = artists.toBackupFile()
    val dir = File(context.filesDir, "backups").apply { mkdirs() }
    val file = File(dir, backupName(backup.timeCreatedAt))
    file.writeText(Json.encodeToString(backup))
    return file
}