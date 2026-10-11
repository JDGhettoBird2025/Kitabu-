package com.example.kitabu.repository

import com.example.kitabu.data.local.entity.Booking
import com.example.kitabu.data.local.entity.BookingDao
import com.example.kitabu.data.local.entity.ActiveBookingWithBook
import kotlinx.coroutines.flow.Flow

class BookingRepositoryImpl(
    private val bookingDao: BookingDao
) : BookingRepository {

    override val bookings: Flow<List<Booking>> =
        bookingDao.getAllBookings()

    override suspend fun getBookingById(bookingId: Int): Booking? {
        return bookingDao.getBookingById(bookingId)
    }

    override fun getBookingsByStatus(status: String): Flow<List<Booking>> {
        return bookingDao.getBookingsByStatus(status)
    }

    override fun getActiveBookingsWithBooks(): Flow<List<ActiveBookingWithBook>> {
        return bookingDao.getActiveBookingsWithBooks()
    }

    override suspend fun getReturnedBookings(): List<Booking> {
        return bookingDao.getReturnedBookings()
    }

    override suspend fun insertBooking(booking: Booking) {
        bookingDao.insertBooking(booking)
    }

    override suspend fun updateBooking(booking: Booking) {
        bookingDao.updateBooking(booking)
    }

    override suspend fun deleteBooking(booking: Booking) {
        bookingDao.deleteBooking(booking)
    }

    override suspend fun deletePendingBooking(bookingId: Int) {
        bookingDao.deletePendingBooking(bookingId)
    }
}