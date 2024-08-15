package org.akai.sciclubhub.data.event

import org.akai.sciclubhub.data.UUID
import java.time.Instant

data class Event(
    val uuid: UUID,
    val title: String,
    val description: String,
    val location: String,
    val timestamp: Instant,
    val open: Boolean,
    val participants: List<UUID>,
    val organisers: List<EventOrganiser>
) {
    val close
        get() = !open
}
