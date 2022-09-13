package me.branwin.boojet.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.branwin.boojet.data.BoojetDatabase
import me.branwin.boojet.data.Category
import me.branwin.boojet.data.CategoryRepository
import me.branwin.boojet.data.EntryRepository

class MainViewModel(application: Application): ViewModel() {
    val allCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())

    private val categoryRepository: CategoryRepository
    private val entryRepository: EntryRepository

    init {
        val db = BoojetDatabase.getInstance(application.applicationContext)
        categoryRepository = CategoryRepository(db.categoryDao())
        entryRepository = EntryRepository(db.entryDao())

        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                allCategories.value = categories
            }
        }
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}