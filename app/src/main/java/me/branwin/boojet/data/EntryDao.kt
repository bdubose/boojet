package me.branwin.boojet.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert
    suspend fun insert(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

    @Update
    suspend fun update(entry: Entry)

    @Query("select * from entry order by timestamp desc")
    fun getAll(): Flow<List<Entry>>

    @Query("select * from entry where categoryId = :categoryId order by timestamp desc")
    fun getAllByCategory(categoryId: Long): Flow<List<Entry>>
}