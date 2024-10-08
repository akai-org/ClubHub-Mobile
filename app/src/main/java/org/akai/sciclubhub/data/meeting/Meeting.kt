package org.akai.sciclubhub.data.meeting

import org.akai.sciclubhub.data.UUID
import java.time.Instant

data class Meeting (
    val uuid: UUID,
    val title: String,
    val description: String,
    val date: Instant,
    val place: String,
    val activeParticipants: List<UUID>,
    val passiveParticipants: List<UUID>,
    val open: Boolean
)