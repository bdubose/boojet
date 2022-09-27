package me.branwin.boojet.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert
    suspend fun insert(entry: Entry): Long

    @Delete
    suspend fun delete(entry: Entry)

    @Update
    suspend fun update(entry: Entry)

    @Query("select * from Entry order by timestamp desc")
    fun getAll(): Flow<List<Entry>>

    @Query("""
        select e.*
        from Entry e
        inner join EntryCategoryCrossRef ec
        on ec.entryId = e.entryId and ec.categoryId = :categoryId
        order by timestamp desc""")
    fun getAllByCategory(categoryId: Long): Flow<List<Entry>>

    @Transaction
    @Query("select * from Entry order by timestamp desc")
    fun getEntriesWithCategories(): Flow<List<EntryWithCategories>>

    @Query("insert into EntryCategoryCrossRef(entryId, categoryId) values (:entryId, :categoryId)")
    suspend fun addCategoryToEntry(entryId: Long, categoryId: Long)
}