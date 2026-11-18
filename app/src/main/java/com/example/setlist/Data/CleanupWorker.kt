package com.example.setlist.Data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@HiltWorker
class CleanupWorker @AssistedInject constructor(@Assisted context: Context, @Assisted params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val keep = inputData.getInt(KEY_KEEP, DEFAULT_KEEP)

        val deletedCount = withContext(Dispatchers.IO) {
            val directory = File(applicationContext.filesDir, "backups")
            val names = directory.list()?.toList().orEmpty()

            backupsToDelete(names, keep).count { name ->
                File(directory, name).delete()
            }
        }

        return Result.success(workDataOf(KEY_DELETED_COUNT to deletedCount))
    }

    companion object {
        const val KEY_KEEP = "keep"
        const val KEY_DELETED_COUNT = "deleted_count"
        const val DEFAULT_KEEP = 5
    }
}