package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.event.EventRaw
import org.akai.sciclubhub.data.event.mapEvent

suspend fun getEvent(authorization: Authorized, eventUUID: UUID) =
    client.get<EventRaw> {
        url {
            path("event", eventUUID.toString())
        }
        header(HttpHeaders.Authorization, authorization.token)
    }.let(::mapEvent)