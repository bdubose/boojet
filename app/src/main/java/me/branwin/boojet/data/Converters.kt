package me.branwin.boojet.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToLong(value: Date?): Long? {
        return value?.time
    }
}