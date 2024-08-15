package org.akai.sciclubhub.data.club

import org.akai.sciclubhub.data.event.Event
import org.akai.sciclubhub.data.meeting.Meeting
import org.akai.sciclubhub.data.member.Member
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.university.University

data class Club(
    val uuid: UUID,
    val name: String,
    val open: Boolean,
    val uniList: List<University>,
    val meetsList: List<Meeting>,
    val eventsList: List<Event>,
    val projectsList: List<UUID>, //TODO: add projects
    val memberList: List<Member>
)
