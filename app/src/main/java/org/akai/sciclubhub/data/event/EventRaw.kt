package org.akai.sciclubhub.data.event

import org.akai.sciclubhub.data.UUID
import java.time.Instant

data class EventRaw(
    val uuid: String,
    val title: String,
    val description: String,
    val location: String,
    val timestamp: Long,
    val open: Boolean,
    val participants: List<String>,
    val organisers: List<EventOrganiser>
) {
    val close
        get() = !open
}

fun mapEvent(event: EventRaw) =
    Event(
        uuid = UUID(event.uuid),
        title = event.title,
        description = event.description,
        location = event.location,
        timestamp = Instant.ofEpochMilli(event.timestamp),
        open = event.open,
        participants = event.participants.map { UUID(it) },
        organisers = event.organisers
    )
