package me.branwin.boojet.data

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun insertCategory(category: Category) =
        categoryDao.insert(category)

    suspend fun deleteCategory(category: Category) =
        categoryDao.delete(category)

    suspend fun updateCategory(category: Category) =
        categoryDao.update(category)

    fun getAllCategories() =
        categoryDao.getAll()

    fun getAllCategoriesWithEntries() =
        categoryDao.getWithEntries()
}