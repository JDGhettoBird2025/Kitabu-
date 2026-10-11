package com.example.kitabu.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert
    suspend fun insertBooking(booking: Booking)

    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE bookingId = :bookingId")
    suspend fun getBookingById(bookingId: Int): Booking?

    @Query("SELECT * FROM bookings WHERE status = :status")
    fun getBookingsByStatus(status: String): Flow<List<Booking>>

    @Query("""
    SELECT
        bookings.*,
        books.id AS book_id,
        books.title AS book_title,
        books.author AS book_author,
        books.category AS book_category,
        books.description AS book_description,
        books.available AS book_available
    FROM bookings
    INNER JOIN books ON bookings.bookOwnerId = books.id
    WHERE bookings.status = 'Active'
""")
    fun getActiveBookingsWithBooks(): Flow<List<ActiveBookingWithBook>>

    @Query("SELECT * FROM bookings WHERE status = 'Pending'")
    fun getPendingBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE status = 'Returned'")
    suspend fun getReturnedBookings(): List<Booking>

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("DELETE FROM bookings WHERE bookingId = :bookingId AND status = 'Pending'")
    suspend fun deletePendingBooking(bookingId: Int)
}