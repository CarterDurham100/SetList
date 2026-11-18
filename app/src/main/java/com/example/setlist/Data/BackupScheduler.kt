package com.example.setlist.Data

import androidx.compose.runtime.mutableStateListOf
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupScheduler @Inject constructor(private val workManager: WorkManager) {
    val backupNowStatus: Flow<BackupStatus> =
        workManager.getWorkInfosForUniqueWorkFlow(BACKUPANDCLEAN_NOW_NAME)
            .map { it.toBackupStatus() }

    fun scheduleBackups(keep: Int = CleanupWorker.DEFAULT_KEEP) {
        val dailyConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val backupRequest = PeriodicWorkRequestBuilder<BackupWorker>(24, TimeUnit.HOURS)
            .setConstraints(dailyConstraints)
            .build()


        val weeklyConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val cleanupRequest = PeriodicWorkRequestBuilder<CleanupWorker>(7, TimeUnit.DAYS)
            .setInputData(workDataOf(CleanupWorker.KEY_KEEP to keep))
            .setConstraints(weeklyConstraints)
            .build()

        workManager.enqueueUniquePeriodicWork(BACKUP_NAME, ExistingPeriodicWorkPolicy.KEEP, backupRequest)
        workManager.enqueueUniquePeriodicWork(CLEANUP_NAME, ExistingPeriodicWorkPolicy.KEEP, cleanupRequest)
    }

    fun backupNow(keep: Int = CleanupWorker.DEFAULT_KEEP) {
        val backupConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val cleanupConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val backupRequest = OneTimeWorkRequestBuilder<BackupWorker>()
            .setConstraints(backupConstraints)
            .build()

        val cleanupRequest = OneTimeWorkRequestBuilder<CleanupWorker>()
            .setInputData(workDataOf(CleanupWorker.KEY_KEEP to keep))
            .setConstraints(cleanupConstraints)
            .build()

        workManager
            .beginUniqueWork(BACKUPANDCLEAN_NOW_NAME, ExistingWorkPolicy.KEEP, backupRequest)
            .then(cleanupRequest)
            .enqueue()
    }

    fun cancel() {
        workManager.cancelUniqueWork(BACKUP_NAME)
        workManager.cancelUniqueWork(CLEANUP_NAME)
        workManager.cancelUniqueWork(BACKUPANDCLEAN_NOW_NAME)
    }

    companion object {
        const val BACKUP_NAME = "setlist_backup_daily"
        const val CLEANUP_NAME = "setlist_cleanup_weekly"
        const val BACKUPANDCLEAN_NOW_NAME = "setlist_backup_now"
    }
}