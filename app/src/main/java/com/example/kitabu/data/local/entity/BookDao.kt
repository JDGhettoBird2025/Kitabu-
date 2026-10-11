package com.example.kitabu.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao{

    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM books")
    fun getAllBook(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE available = 1")
    fun getAvailableBooks(): Flow<List<Book>>

    @Query("""
    SELECT * FROM books
    WHERE title LIKE '%' || :searchQuery || '%'
    OR author LIKE '%' || :searchQuery || '%'
""")
    fun searchBooks(searchQuery: String): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book?

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}