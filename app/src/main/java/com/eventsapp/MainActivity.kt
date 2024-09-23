package com.eventsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.eventsapp.data.Event
import com.eventsapp.data.EventDatabase
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etEventDate = findViewById<EditText>(R.id.etEventDate)
        val etEventTime = findViewById<EditText>(R.id.etEventTime)

        // Date Picker Dialog
        etEventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etEventDate.setText(date)
            }, year, month, day)

            datePickerDialog.show()
        }

        // Time Picker Dialog
        etEventTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val time = "$selectedHour:$selectedMinute"
                etEventTime.setText(time)
            }, hour, minute, true)

            timePickerDialog.show()
        }

        // View Events Button
        findViewById<Button>(R.id.btnViewEvents).setOnClickListener {
            val intent = Intent(this, EventListActivity::class.java)
            startActivity(intent)
        }

        // Save Button Click Listener
        findViewById<Button>(R.id.btnSaveEvent).setOnClickListener {
            saveEvent()
        }
    }

    private fun saveEvent() {
        val eventName = findViewById<EditText>(R.id.etEventName).text.toString()
        val eventDate = findViewById<EditText>(R.id.etEventDate).text.toString()
        val eventTime = findViewById<EditText>(R.id.etEventTime).text.toString()
        val eventLocation = findViewById<EditText>(R.id.etEventLocation).text.toString()
        val eventDescription = findViewById<EditText>(R.id.etEventDescription).text.toString()

        if (eventName.isEmpty() || eventDate.isEmpty() || eventTime.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create Event object
        val event = Event(
            eventName = eventName,
            eventDate = eventDate,
            eventTime = eventTime,
            eventLocation = eventLocation,
            eventDescription = eventDescription
        )

        // Save event in the database using a coroutine
        val eventDao = EventDatabase.getDatabase(applicationContext).eventDao()
        lifecycleScope.launch {
            eventDao.insert(event)
            Toast.makeText(this@MainActivity, "Event saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
