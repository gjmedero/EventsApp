package com.eventsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eventsapp.data.Event
import com.eventsapp.data.EventDao
import com.eventsapp.data.EventDatabase
import kotlinx.coroutines.launch

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventDao: EventDao // Declare eventDao here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch events from the database and display in RecyclerView
        eventDao = EventDatabase.getDatabase(applicationContext).eventDao()
        lifecycleScope.launch {
            eventDao.getAllEvents().collect { eventList ->
                eventAdapter = EventAdapter(eventList) { event ->  // Pass the onDeleteEvent lambda here
                    showDeleteConfirmationDialog(event)
                }
                recyclerView.adapter = eventAdapter
            }
        }

        findViewById<Button>(R.id.btnBackToCreateEvent).setOnClickListener {
            // Navigate back to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Call finish() to close the current activity
        }

    }

    // Function to show a delete confirmation dialog
    private fun showDeleteConfirmationDialog(event: Event) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Event")
            setMessage("Are you sure you want to delete this event?")
            setPositiveButton("Yes") { _, _ ->
                deleteEvent(event) // Call delete if confirmed
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    // Function to delete the event from the database
    private fun deleteEvent(event: Event) {
        lifecycleScope.launch {
            eventDao.delete(event) // Remove the event
            Toast.makeText(this@EventListActivity, "Event deleted!", Toast.LENGTH_SHORT).show()
        }
    }
}
