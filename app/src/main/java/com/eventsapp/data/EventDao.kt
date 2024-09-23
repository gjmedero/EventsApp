package com.eventsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)  // This must be the 'Event' entity

    @Query("SELECT * FROM event_table ORDER BY eventDate ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Delete
    suspend fun delete(event: Event)  // Add delete method here
}
