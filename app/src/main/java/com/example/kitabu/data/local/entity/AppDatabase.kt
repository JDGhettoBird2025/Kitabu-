package com.example.kitabu.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kitabu.data.local.entity.Book
import com.example.kitabu.data.local.entity.BookDao
@TypeConverters(Converters::class)
@Database(
    entities = [Book::class, Booking::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookingDao(): BookingDao
}