package me.branwin.boojet.data

import androidx.room.*

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val name: String,
    val isExpense: Boolean = true,
)

data class CategoryWithEntries(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "entryId",
        associateBy = Junction(EntryCategoryCrossRef::class)
    )
    val entries: List<Entry> = emptyList()
)