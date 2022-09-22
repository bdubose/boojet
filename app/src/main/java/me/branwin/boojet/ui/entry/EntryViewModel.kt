package me.branwin.boojet.ui.entry

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import me.branwin.boojet.data.Category

class EntryViewModel : ViewModel() {
    private val _selectedCategories = mutableStateListOf<Category>()
    val selectedCategories: List<Category>
        get() = _selectedCategories

    fun addCategory(category: Category) {
        _selectedCategories.add(category)
    }

    fun removeCategory(category: Category) {
        _selectedCategories.remove(category)
    }

    fun reset() {
        _selectedCategories.clear()
    }
}