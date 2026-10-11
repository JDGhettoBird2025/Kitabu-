package com.example.kitabu.repository

import com.example.kitabu.data.local.entity.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    val books: Flow<List<Book>>

    fun getAvailableBooks(): Flow<List<Book>>

    fun searchBooks(searchQuery: String): Flow<List<Book>>

    suspend fun getBookById(bookId: Int): Book?

    suspend fun insertBook(book: Book)

    suspend fun updateBook(book: Book)

    suspend fun deleteBook(book: Book)
}