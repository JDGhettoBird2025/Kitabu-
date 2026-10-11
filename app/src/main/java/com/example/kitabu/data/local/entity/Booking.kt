package com.example.kitabu.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "bookings",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["bookOwnerId"])]
)

data class Booking(
    @PrimaryKey(autoGenerate = true)
    val bookingId: Int = 0,

    val bookOwnerId: Int,

    val userName: String,

    val bookingDate: Long,

    val returnDeadline: Long,

    val status: String
)