package me.branwin.boojet.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(vararg categories: Category)

    @Delete
    suspend fun delete(category: Category)

    @Update
    suspend fun update(category: Category)

    @Query("select * from category order by name")
    fun getAll(): Flow<List<Category>>

    @Transaction
    @Query("select * from category")
    fun getWithEntries(): Flow<List<CategoryWithEntries>>

    // if we have one category and don't want to get a flow update
    // when an unrelated category changes, we need to use flow.distinctUntilChanged
    // see: https://medium.com/androiddevelopers/room-flow-273acffe5b57

    // flow & livedata re lifecycle
    // see: https://medium.com/@msasikanth/with-flow-you-will-have-access-to-multiple-operators-you-can-use-to-transform-the-data-fedd560a0c2e
}