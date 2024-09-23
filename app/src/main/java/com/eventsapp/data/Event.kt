package com.eventsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventName: String,
    val eventDate: String,
    val eventTime: String,
    val eventLocation: String,
    val eventDescription: String
)
