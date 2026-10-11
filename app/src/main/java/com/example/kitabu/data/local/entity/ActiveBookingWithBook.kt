package com.example.kitabu.data.local.entity

import androidx.room.Embedded

data class ActiveBookingWithBook(
    @Embedded
    val booking: Booking,

    @Embedded(prefix = "book_")
    val book: Book
)

