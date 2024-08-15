package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.eventsPath
import org.akai.sciclubhub.clubhubclient.organiserTypesPath
import org.akai.sciclubhub.data.event.EventOrganiserType

suspend fun getOrganiserTypes() =
    client.get<List<EventOrganiserType>> {
        url {
            path(
                eventsPath,
                organiserTypesPath
            )
        }
    }