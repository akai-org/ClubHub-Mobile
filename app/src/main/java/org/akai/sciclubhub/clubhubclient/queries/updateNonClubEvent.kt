package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.eventsPath
import org.akai.sciclubhub.data.event.Event
import org.akai.sciclubhub.data.event.EventRaw
import org.akai.sciclubhub.data.UUID
import java.time.Instant

suspend fun updateNonClubEvent(
    authorization: Authorized,
    uuid: UUID,
    title: String? = null,
    description: String? = null,
    timestamp: Instant? = null,
    location: String? = null,
    open: Boolean? = null,
    participants: List<UUID>? = null,
) = mutableMapOf<String, Any?>(
        "uuid" to uuid.value
    ).let {
        title?.let {title ->
            it["title"] = title
        }
        description?.let {description ->
            it["description"] = description
        }
        timestamp?.let {timestamp ->
            it["timestamp"] = timestamp.toEpochMilli()
        }
        location?.let {location ->
            it["location"] = location
        }
        open?.let {open ->
            it["open"] = open
        }
        participants?.let { participants ->
            it["participants"] = participants.map {
                participantUUID -> participantUUID.value
            }
        }

        return@let it
    }.let { requestBody ->
        client.put<EventRaw> {
            url {
                path(eventsPath)
            }
            header(HttpHeaders.Authorization, authorization.token)
            body = requestBody
        }
    }.let {
        Event(
            uuid = UUID(it.uuid),
            title = it.title,
            description = it.description,
            timestamp = Instant.ofEpochMilli(it.timestamp),
            location = it.location,
            open = it.open,
            participants = it.participants.map {participantUUID -> UUID(participantUUID) },
            organisers = it.organisers,
        )
    }