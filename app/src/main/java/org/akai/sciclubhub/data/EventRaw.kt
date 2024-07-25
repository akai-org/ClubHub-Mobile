package org.akai.sciclubhub.data

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
