package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.eventsPath
import org.akai.sciclubhub.data.event.Event
import org.akai.sciclubhub.data.event.EventRaw
import org.akai.sciclubhub.data.UUID
import java.time.Instant

suspend fun getNonClubEvents(authorization: Authorized) =
    client.get<List<EventRaw>> {
        url {
            path(eventsPath)
        }
        header(HttpHeaders.Authorization, authorization.token)
    }.let {
        it.map { event ->
            Event(
                uuid = UUID(event.uuid),
                title = event.title,
                description = event.description,
                timestamp = Instant.ofEpochMilli(event.timestamp),
                location = event.location,
                open = event.open,
                participants = event.participants.map { participantUUID -> UUID(participantUUID) },
                organisers = event.organisers,
            )
        }
    }