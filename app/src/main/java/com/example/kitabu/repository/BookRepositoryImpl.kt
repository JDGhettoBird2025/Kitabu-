package com.example.kitabu.repository

import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.data.local.entity.BookDao
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val bookDao: BookDao
) : BookRepository {

    override val books: Flow<List<Book>> =
        bookDao.getAllBook()

    override fun getAvailableBooks(): Flow<List<Book>> {
        return bookDao.getAvailableBooks()
    }

    override fun searchBooks(searchQuery: String): Flow<List<Book>> {
        return bookDao.searchBooks(searchQuery)
    }

    override suspend fun getBookById(bookId: Int): Book? {
        return bookDao.getBookById(bookId)
    }

    override suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}