package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.eventsPath
import org.akai.sciclubhub.data.event.Event
import org.akai.sciclubhub.data.event.EventRaw
import org.akai.sciclubhub.data.UUID
import java.time.Instant

suspend fun postNewNonClubEvent(authorization: Authorized, event: Event) =
    client.post<EventRaw> {
        url {
            path(eventsPath)
        }
        header(HttpHeaders.Authorization, authorization.token)
        body = mapOf(
            "title" to event.title,
            "description" to event.description,
            "timestamp" to event.timestamp.toEpochMilli(),
            "location" to event.location,
            "open" to event.open,
            "participants" to event.participants.map { it.value },
            "organisers" to event.organisers,
        )
    }.let {
        Event(
            uuid = UUID(it.uuid),
            title = it.title,
            description = it.description,
            timestamp = Instant.ofEpochMilli(it.timestamp),
            location = it.location,
            open = it.open,
            participants = it.participants.map { participantUUID -> UUID(participantUUID) },
            organisers = it.organisers,
        )
    }