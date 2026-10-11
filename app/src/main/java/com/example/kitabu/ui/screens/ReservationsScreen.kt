package com.example.kitabu.ui.screens

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.viewmodel.BookViewModel
import com.example.kitabu.viewmodel.BookingViewModel




@Composable
fun ReservationsScreen(
    selectedDuration: Int,
    bookingViewModel: BookingViewModel,
    bookViewModel: BookViewModel
) {
    val pendingBookings by bookingViewModel
        .getBookingsByStatus("Pending")
        .collectAsState(initial = emptyList())

    if (pendingBookings.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Reservations",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("No pending reservations")
        }
        return
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
            text = "My Reservations",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = pendingBookings,
                key = { it.bookingId }
            ) { booking ->

                var reservedBook by remember(booking.bookingId) {
                    mutableStateOf<Book?>(null)
                }

                LaunchedEffect(booking.bookOwnerId) {
                    reservedBook =
                        bookViewModel.getBookById(booking.bookOwnerId)
                }

                val rentalDuration =
                    ((booking.returnDeadline - booking.bookingDate) /
                            (24 * 60 * 60 * 1000L)).toInt()

                val formattedReservationDate =
                    SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        .format(Date(booking.bookingDate))

                val formattedReturnDeadline =
                    SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        .format(Date(booking.returnDeadline))

                val millisecondsPerDay = 24 * 60 * 60 * 1000L
                val millisecondsRemaining =
                    booking.returnDeadline - System.currentTimeMillis()

                val daysRemaining = if (millisecondsRemaining <= 0L) {
                    0
                } else {
                    kotlin.math.ceil(
                        millisecondsRemaining.toDouble() /
                                millisecondsPerDay
                    ).toInt()
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
                            text = reservedBook?.title ?: "Loading book...",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = reservedBook?.author ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Reservation ID: ${booking.bookingId}")
                        Text(
                            text = "Status: Pending Pickup",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("Rental Duration: $rentalDuration days")
                        Text("Reservation Date: $formattedReservationDate")
                        Text("Days Remaining: $daysRemaining days")
                        Text("Return Deadline: $formattedReturnDeadline")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your reservation is waiting for pickup. " +
                                    "You can cancel it if you no longer need the book.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                bookingViewModel.deletePendingBooking(
                                    booking.bookingId
                                )

                                reservedBook?.let { book ->
                                    bookViewModel.updateBook(
                                        book.copy(available = true)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancel Reservation")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                bookingViewModel.updateBooking(
                                    booking.copy(status = "Active")
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirm Pickup")
                        }
                    }
                }
            }
        }
    }
}
