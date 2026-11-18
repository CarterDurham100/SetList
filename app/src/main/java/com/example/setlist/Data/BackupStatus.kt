package com.example.setlist.Data

import androidx.work.WorkInfo

sealed interface BackupStatus {
    data object Idle : BackupStatus
    data object Running : BackupStatus
    data class Finished(val numOfArtistsBackedUp: Int) : BackupStatus
    data object Failed : BackupStatus
}

fun List<WorkInfo>.toBackupStatus(): BackupStatus {
    if (this.size == 0) {
        return BackupStatus.Idle
    }

    var anyRunning = false
    var anyFailed = false
    var succeeded = true

    for (info in this) {
        when(info.state) {
            WorkInfo.State.ENQUEUED,
            WorkInfo.State.BLOCKED,
            WorkInfo.State.RUNNING -> {
                anyRunning = true
                succeeded = false
            }
            WorkInfo.State.FAILED -> {
                anyFailed = true
                succeeded = false
            }
            WorkInfo.State.SUCCEEDED -> {}
            WorkInfo.State.CANCELLED -> succeeded = false
        }
    }

    if (anyRunning) {
        return BackupStatus.Running
    }

    if (anyFailed) {
        return BackupStatus.Failed
    }

    if (succeeded) {
        var count = 0
        for (info in this) {
            if (info.tags.contains(BackupWorker::class.java.name)) {
                count = info.outputData.getInt(BackupWorker.KEY_ARTIST_COUNT, 0)
            }
        }
        return BackupStatus.Finished(count)
    }

    return BackupStatus.Idle
}