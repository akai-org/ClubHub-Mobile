package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.eventsPath
import org.akai.sciclubhub.clubhubclient.joinPath
import org.akai.sciclubhub.data.event.Event
import org.akai.sciclubhub.data.event.EventRaw
import org.akai.sciclubhub.data.UUID
import java.time.Instant

suspend fun joinNonClubEvent(authorization: Authorized, eventUUID: UUID) =
    client.post<EventRaw> {
        url {
            path(
                eventsPath,
                joinPath
            )
        }
        header(HttpHeaders.Authorization, authorization.token)
        body = mapOf(
            "uuid" to eventUUID.value
        )
    }.let {
        Event(
            uuid = UUID(it.uuid),
            title = it.title,
            description = it.description,
            timestamp = Instant.ofEpochMilli(it.timestamp),
            location = it.location,
            open = it.open,
            participants = it.participants.map {participantUUID ->  UUID(participantUUID) },
            organisers = it.organisers,
        )
    }
