package org.akai.sciclubhub.data

data class MeetingRaw(
    val uuid: String,
    val title: String,
    val description: String,
    val timestamp: Long,
    val place: String,
    val activeParticipants: List<String>,
    val passiveParticipants: List<String>,
    val open: Boolean
)
