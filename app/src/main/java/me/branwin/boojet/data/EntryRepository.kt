package me.branwin.boojet.data

class EntryRepository(private val entryDao: EntryDao) {
    suspend fun insertEntry(entryWc: EntryWithCategories) {
        val entryId = entryDao.insert(entryWc.entry)
        entryWc.categories.forEach {
            entryDao.addCategoryToEntry(entryId, it.categoryId)
        }
    }

    suspend fun deleteEntry(entry: Entry) =
        entryDao.delete(entry)

    suspend fun updateEntry(entry: Entry) =
        entryDao.update(entry)

    fun getAllEntries() =
        entryDao.getAll()

    fun getAllEntriesByCategory(categoryId: Long) =
        entryDao.getAllByCategory(categoryId)

    fun getAllEntriesWithCategories() =
        entryDao.getEntriesWithCategories()
}