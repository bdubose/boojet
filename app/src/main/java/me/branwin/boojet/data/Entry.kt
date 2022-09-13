package me.branwin.boojet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Entry(
    @PrimaryKey val id: Long,
    val name: String,
    val amount: Float,
    val categoryId: Long,
    val timestamp: Date,
)