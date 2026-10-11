package com.example.kitabu.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.data.local.entity.Booking
import com.example.kitabu.viewmodel.BookingViewModel
import com.example.kitabu.viewmodel.BookViewModel

@Composable
fun BookDetailsScreen(
    books: List<Book>,
    bookingViewModel: BookingViewModel,
    bookViewModel: BookViewModel,
    onReserveBookClick: (Int) -> Unit
) {
    var showReservationDialog by remember {mutableStateOf(false)}
    var selectedDuration by remember {mutableStateOf(7)}
    var searchQuery by remember { mutableStateOf("") }
    val searchedBooks by bookViewModel.searchedBooks.collectAsState(
        initial = emptyList()
    )
    var selectedCategory by remember { mutableStateOf("All") }
    var categoryMenuExpanded by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    val categories = listOf(
        "All",
        "Crime",
        "Comedy",
        "Contemporary Fiction",
        "Mystery",
        "Literary Fiction",
        "Historical Fiction"
    )

    val filteredBooks = searchedBooks.filter { book ->
        selectedCategory == "All" ||
                book.category == selectedCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Book Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                bookViewModel.updateSearchQuery(it)
            },
            label = {
                Text("Search books")
            },
            placeholder = {
                Text("Search by title or author")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            OutlinedButton(
                onClick = { categoryMenuExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Category: $selectedCategory")
            }

            DropdownMenu(
                expanded = categoryMenuExpanded,
                onDismissRequest = { categoryMenuExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            categoryMenuExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        selectedBook?.let { book ->
            Text(
                text = "Selected: ${book.title}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                if (selectedBook != null) {
                    showReservationDialog = true
                }
            },
            enabled = selectedBook?.available == true,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reserve Book")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredBooks) { book ->


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable {
                            selectedBook = book

                            if (book.available) {
                                showReservationDialog = true
                            }
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = book.category,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = book.description,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (book.available) "✓ Available" else "✕ Unavailable",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .background(
                                    color = if (book.available) Color(0xFF2E7D32)
                                    else Color(0xFFC62828),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        if(showReservationDialog){
            AlertDialog(
                onDismissRequest = {
                    showReservationDialog = false
                },
                title = {
                    Text("Reserve Book")
                },
                text = {
                    Column{
                        Text(
                            text = "Book: ${selectedBook?.title ?: "No book selected"}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Choose rental duration:")

                        Spacer(modifier = Modifier.height(12.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf(7, 14, 21).forEach { duration ->
                                if (selectedDuration == duration) {
                                    Button(
                                        onClick = {
                                            selectedDuration = duration
                                        }
                                    ) {
                                        Text("$duration Days")
                                    }
                                } else {
                                    TextButton(
                                        onClick = {
                                            selectedDuration = duration
                                        }
                                    ) {
                                        Text("$duration Days")
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedBook?.let { book ->

                                val currentTime = System.currentTimeMillis()
                                val returnDeadline =
                                    currentTime + (selectedDuration * 24 * 60 * 60 * 1000L)

                                val booking = Booking(
                                    bookOwnerId = book.id,
                                    userName = "Joel",
                                    bookingDate = currentTime,
                                    returnDeadline = returnDeadline,
                                    status = "Pending"
                                )

                                bookingViewModel.addBooking(booking)
                                bookViewModel.updateBook(book.copy(available = false))

                                showReservationDialog = false
                                onReserveBookClick(selectedDuration)
                            }
                        }
                    ) {
                        Text("Reserve")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showReservationDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
