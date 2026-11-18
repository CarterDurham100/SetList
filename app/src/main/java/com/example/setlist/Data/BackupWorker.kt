package com.example.setlist.Data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException
import com.example.setlist.Data.writeBackup

//a worker has to be able to stop because workmanager can stop at anytime, so worker needs to stop quickly(?)
@HiltWorker
class BackupWorker @AssistedInject constructor(@Assisted context: Context, @Assisted params: WorkerParameters, private val repository: ArtistRepository) : CoroutineWorker(context, params){
    override suspend fun doWork(): Result {
        return try {
            val artists = repository.artists.first()

            if (artists.isEmpty()) {
                return Result.success()
            }

            val file = withContext(Dispatchers.IO) {
                writeBackup(applicationContext, artists)
            }

            Result.success((
                workDataOf(KEY_ARTIST_COUNT to artists.size, KEY_FILE_NAME to file.name)
            ))
        } catch(e: IOException) {
            if (runAttemptCount < MAX_ATTEMPTS) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val KEY_ARTIST_COUNT = "artist_count"
        const val KEY_FILE_NAME = "file_name"
        private const val MAX_ATTEMPTS = 3
    }
}