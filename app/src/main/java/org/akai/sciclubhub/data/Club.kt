package org.akai.sciclubhub.data

data class Club(
    val uuid: UUID,
    val name: String,
    val open: Boolean,
    val uniList: List<UUID>,
    val meetsList: List<UUID>,
    val eventsList: List<UUID>,
    val projectsList: List<UUID>,
    val memberList: List<Member>
)
