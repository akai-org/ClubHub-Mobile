package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.meeting.MeetingRaw
import org.akai.sciclubhub.data.meeting.mapMeeting

suspend fun getMeeting(meetingUUID: UUID) =
    client.get<MeetingRaw> {
        url {
            path("meeting", meetingUUID.toString())
        }
    }.let(::mapMeeting)