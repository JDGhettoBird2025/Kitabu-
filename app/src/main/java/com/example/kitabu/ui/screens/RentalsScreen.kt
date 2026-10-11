package com.example.kitabu.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.viewmodel.BookingViewModel
import com.example.kitabu.viewmodel.BookViewModel

@Composable
fun RentalsScreen(
    bookingViewModel: BookingViewModel,
    bookViewModel: BookViewModel,
    onViewRentalHistoryClick: () -> Unit
) {

    val activeBookings by bookingViewModel
        .getBookingsByStatus("Active")
        .collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        val returnedBookings = bookingViewModel.getReturnedBookings()
        val activeBookIds = activeBookings.map { it.bookOwnerId }.toSet()

        returnedBookings
            .map { it.bookOwnerId }
            .distinct()
            .filter { it !in activeBookIds }
            .forEach { bookId ->
                val book = bookViewModel.getBookById(bookId)

                if (book != null && !book.available) {
                    bookViewModel.updateBook(book.copy(available = true))
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "My Rentals",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (activeBookings.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "No Active Rentals",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "You currently have no books checked out.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Books that you rent will appear here.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            activeBookings.forEach { booking ->

                var rentalBook by remember(booking.bookingId) {
                    mutableStateOf<Book?>(null)
                }

                LaunchedEffect(booking.bookOwnerId) {
                    rentalBook = bookViewModel.getBookById(booking.bookOwnerId)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = rentalBook?.title ?: "Loading book...",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = rentalBook?.author ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Rental ID: ${booking.bookingId}")

                        Text("Status: ${booking.status}")

                        Text(
                            text = "Reservation Date: ${
                                java.text.SimpleDateFormat(
                                    "dd MMMM yyyy",
                                    java.util.Locale.getDefault()
                                ).format(java.util.Date(booking.bookingDate))
                            }"
                        )

                        Text(
                            text = "Rental Duration: ${
                                ((booking.returnDeadline - booking.bookingDate) /
                                        (24 * 60 * 60 * 1000L)).toInt()
                            } days"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Return Deadline: ${
                                java.text.SimpleDateFormat(
                                    "dd MMMM yyyy",
                                    java.util.Locale.getDefault()
                                ).format(java.util.Date(booking.returnDeadline))
                            }"
                        )

                        val millisecondsPerDay = 24 * 60 * 60 * 1000L
                        val currentTime = System.currentTimeMillis()
                        val millisecondsRemaining = booking.returnDeadline - currentTime

                        val daysRemaining = kotlin.math.ceil(
                            millisecondsRemaining.toDouble() / millisecondsPerDay
                        ).toInt()

                        Text(
                            text = when {
                                daysRemaining < 0 -> "Overdue by ${-daysRemaining} days"
                                daysRemaining == 0 -> "Due today"
                                daysRemaining == 1 -> "1 day remaining"
                                else -> "$daysRemaining days remaining"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (daysRemaining <= 1) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                bookingViewModel.updateBooking(
                                    booking.copy(status = "Returned")
                                )

                                val returnedBook = rentalBook

                                if (returnedBook != null) {
                                    bookViewModel.updateBook(
                                        returnedBook.copy(available = true)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Return Book")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                if (booking.status == "Active") {
                                    val renewalDays = 7
                                    val millisecondsPerDay = 24 * 60 * 60 * 1000L

                                    val renewedBooking = booking.copy(
                                        returnDeadline = booking.returnDeadline +
                                                (renewalDays * millisecondsPerDay)
                                    )

                                    bookingViewModel.updateBooking(renewedBooking)
                                }
                            },
                            enabled = booking.status == "Active",
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Renew for 7 Days")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                onViewRentalHistoryClick()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "View Rental History"
            )
        }
    }
}
