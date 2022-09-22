package me.branwin.boojet.data

import androidx.room.*
import java.util.Date

@Entity
data class Entry(
    @PrimaryKey(autoGenerate = true) val entryId: Long = 0,
    val name: String,
    val amount: Float,
    val timestamp: Date = Date(),
)

data class EntryWithCategories(
    @Embedded val entry: Entry,
    @Relation(
        parentColumn = "entryId",
        entityColumn = "categoryId",
        associateBy = Junction(EntryCategoryCrossRef::class)
    )
    val categories: List<Category>

)