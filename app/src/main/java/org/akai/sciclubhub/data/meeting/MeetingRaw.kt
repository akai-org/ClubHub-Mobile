package org.akai.sciclubhub.data.meeting

import org.akai.sciclubhub.data.UUID
import java.time.Instant

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

fun mapMeeting(rawMeeting: MeetingRaw) = Meeting (
uuid = UUID(rawMeeting.uuid),
title = rawMeeting.title,
description = rawMeeting.description,
date = Instant.ofEpochMilli(rawMeeting.timestamp),
place = rawMeeting.place,
activeParticipants = rawMeeting.activeParticipants.map { userUUID -> UUID(userUUID) },
passiveParticipants = rawMeeting.passiveParticipants.map { userUUID -> UUID(userUUID) },
open = rawMeeting.open,
)
