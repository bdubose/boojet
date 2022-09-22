package me.branwin.boojet.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["entryId", "categoryId"])
data class EntryCategoryCrossRef(
    @ColumnInfo(index = true) val entryId: Long,
    @ColumnInfo(index = true) val categoryId: Long,
)
