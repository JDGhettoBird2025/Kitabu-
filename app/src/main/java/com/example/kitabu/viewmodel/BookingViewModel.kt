package com.example.kitabu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu.data.local.entity.Booking
import com.example.kitabu.repository.BookingRepository
import com.example.kitabu.data.local.entity.ActiveBookingWithBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val repository: BookingRepository
) : ViewModel() {

    val bookings: Flow<List<Booking>> = repository.bookings

    fun addBooking(booking: Booking) {
        viewModelScope.launch {
            repository.insertBooking(booking)
        }
    }

    fun updateBooking(booking: Booking) {
        viewModelScope.launch {
            repository.updateBooking(booking)
        }
    }

    fun deleteBooking(booking: Booking) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
        }
    }

    fun deletePendingBooking(bookingId: Int) {
        viewModelScope.launch {
            repository.deletePendingBooking(bookingId)
        }
    }

    fun getBookingsByStatus(status: String): Flow<List<Booking>> {
        return repository.getBookingsByStatus(status)
    }

    fun getActiveBookingsWithBooks(): Flow<List<ActiveBookingWithBook>> {
        return repository.getActiveBookingsWithBooks()
    }

    suspend fun getBookingById(bookingId: Int): Booking? {
        return repository.getBookingById(bookingId)
    }

    suspend fun getReturnedBookings(): List<Booking> {
        return repository.getReturnedBookings()
    }
}