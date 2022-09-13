package me.branwin.boojet.data

class EntryRepository(private val entryDao: EntryDao) {
    suspend fun insertEntry(entry: Entry) =
        entryDao.insert(entry)

    suspend fun deleteEntry(entry: Entry) =
        entryDao.delete(entry)

    suspend fun updateEntry(entry: Entry) =
        entryDao.update(entry)

    fun getAllEntries() =
        entryDao.getAll()

    fun getAllEntriesByCategory(categoryId: Long) =
        entryDao.getAllByCategory(categoryId)
}