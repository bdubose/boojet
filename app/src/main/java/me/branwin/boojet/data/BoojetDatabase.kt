package me.branwin.boojet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Category::class, Entry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BoojetDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract  fun entryDao(): EntryDao

    companion object {
        @Volatile private var instance: BoojetDatabase? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BoojetDatabase::class.java,
                    "boojet"
                ).build().also { instance = it }
            }
    }
}