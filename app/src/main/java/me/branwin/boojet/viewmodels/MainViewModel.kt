package me.branwin.boojet.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.branwin.boojet.data.*

class MainViewModel(application: Application): ViewModel() {
    val allCategories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val allEntries: MutableStateFlow<List<EntryWithCategories>> = MutableStateFlow(emptyList())

    private val categoryRepository: CategoryRepository
    private val entryRepository: EntryRepository

    init {
        val db = BoojetDatabase.getInstance(application.applicationContext)
        categoryRepository = CategoryRepository(db.categoryDao())
        entryRepository = EntryRepository(db.entryDao())

        viewModelScope.launch {
            categoryRepository.getAllCategories().collect {
                allCategories.value = it
            }
        }
        viewModelScope.launch {
            entryRepository.getAllEntriesWithCategories().collect {
                allEntries.value = it
            }
        }
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun insertEntry(entryWithCategories: EntryWithCategories) {
        viewModelScope.launch {
            entryRepository.insertEntry(entryWithCategories)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}