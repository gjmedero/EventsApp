package com.eventsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eventsapp.data.Event

class EventAdapter(private val eventList: List<Event>,
                   private val onDeleteEvent: (Event) -> Unit // Event to be deleted
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.tvEventName)
        val eventDate: TextView = itemView.findViewById(R.id.tvEventDate)
        val eventLocation: TextView = itemView.findViewById(R.id.tvEventLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventName.text = event.eventName
        holder.eventDate.text = event.eventDate
        holder.eventLocation.text = event.eventLocation

        // Handle long click for deletion
        holder.itemView.setOnLongClickListener {
            onDeleteEvent(event)  // Pass the event to the delete function
            true  // Indicate that the event has been handled
        }
    }

    override fun getItemCount() = eventList.size
}
