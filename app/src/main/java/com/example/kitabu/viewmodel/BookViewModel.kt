package com.example.kitabu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    val books: Flow<List<Book>> = repository.books

    private val searchQuery = MutableStateFlow("")

    val searchedBooks: Flow<List<Book>> =
        searchQuery.flatMapLatest { query ->
            if (query.isBlank()) {
                repository.books
            } else {
                repository.searchBooks(query)
            }
        }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun getAvailableBooks(): Flow<List<Book>> {
        return repository.getAvailableBooks()
    }

    fun searchBooks(searchQuery: String): Flow<List<Book>> {
        return repository.searchBooks(searchQuery)
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    suspend fun getBookById(bookId: Int): Book? {
        return repository.getBookById(bookId)
    }
}