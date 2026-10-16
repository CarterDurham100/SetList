package com.example.setlist.Data.Room

import androidx.room3.ColumnInfo
import androidx.room3.ColumnTypeConverter
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import java.time.Instant

class InstantConverter {
    @ColumnTypeConverter fun fromInstant(value: Instant): Long {
        val longValue = value.toEpochMilli()
        return longValue
    }

    @ColumnTypeConverter fun toInstant(value: Long): Instant {
        val instantValue = Instant.ofEpochMilli(value)
        return instantValue
    }
}