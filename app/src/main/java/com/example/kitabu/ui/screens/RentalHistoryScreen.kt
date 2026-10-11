
package com.example.kitabu.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.viewmodel.BookViewModel
import com.example.kitabu.viewmodel.BookingViewModel

@Composable
fun RentalHistoryScreen(
    bookingViewModel: BookingViewModel,
    bookViewModel: BookViewModel
) {
    val allBookings by bookingViewModel.bookings.collectAsState(
        initial = emptyList()
    )

    val historyBookings = allBookings.filter {
        it.status == "Returned" || it.status == "Cancelled"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Rental History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (historyBookings.isEmpty()) {
            Text(
                text = "No rental history yet.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = historyBookings,
                    key = { it.bookingId }
                ) { booking ->

                    var historyBook by remember(booking.bookingId) {
                        mutableStateOf<Book?>(null)
                    }

                    LaunchedEffect(booking.bookOwnerId) {
                        historyBook = bookViewModel.getBookById(booking.bookOwnerId)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = historyBook?.title ?: "Loading book...",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = historyBook?.author ?: "",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Rental ID: ${booking.bookingId}")

                            Text("Status: ${booking.status}")

                            Text("Status: ${booking.status}")

                            Text(
                                text = "Rental Duration: ${
                                    ((booking.returnDeadline - booking.bookingDate) /
                                            (24 * 60 * 60 * 1000L)).toInt()
                                } days"
                            )

                            Text(
                                text = "Return Deadline: ${
                                    java.text.SimpleDateFormat(
                                        "dd MMMM yyyy",
                                        java.util.Locale.getDefault()
                                    ).format(
                                        java.util.Date(booking.returnDeadline)
                                    )
                                }"
                            )
                        }
                    }
                }
            }
        }
    }
}



