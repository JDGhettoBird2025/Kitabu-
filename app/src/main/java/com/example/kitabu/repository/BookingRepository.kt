package com.example.kitabu.repository

import com.example.kitabu.data.local.entity.Booking
import com.example.kitabu.data.local.entity.ActiveBookingWithBook
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    val bookings: Flow<List<Booking>>

    suspend fun getBookingById(bookingId: Int): Booking?

    fun getBookingsByStatus(status: String): Flow<List<Booking>>

    fun getActiveBookingsWithBooks(): Flow<List<ActiveBookingWithBook>>

    suspend fun getReturnedBookings(): List<Booking>

    suspend fun insertBooking(booking: Booking)

    suspend fun updateBooking(booking: Booking)

    suspend fun deleteBooking(booking: Booking)

    suspend fun deletePendingBooking(bookingId: Int)
}