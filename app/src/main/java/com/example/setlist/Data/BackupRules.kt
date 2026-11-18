package com.example.setlist.Data

import androidx.navigation3.runtime.rememberNavBackStack

private val BACKUP_NAME_PATTERN = Regex("""setback_list_(\d+)\.json""")

fun backupsToDelete(files: List<String>, filesToKeep: Int): List<String> {
    val backups = files.mapNotNull { name ->
        val millis = BACKUP_NAME_PATTERN.matchEntire(name)
            ?.groupValues?.get(1)
            ?.toLongOrNull()
        millis?.let {
            name to it
        }
    }

    val keepCount = filesToKeep.coerceAtLeast(0)

    if(backups.size <= keepCount) {
        return emptyList()
    }

    return backups
        .sortedByDescending { it.second }
        .drop(keepCount)
        .map { it.first}
}