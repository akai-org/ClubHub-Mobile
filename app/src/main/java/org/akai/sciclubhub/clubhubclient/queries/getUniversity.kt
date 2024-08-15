package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.universitiesPath
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.university.UniversityRaw
import org.akai.sciclubhub.data.university.mapUniversity

suspend fun getUniversity(uuid: UUID) =
    client.get<UniversityRaw> {
        url {
            path(universitiesPath)
            parameters.append("uuid", uuid.toString())
        }
    }.let {
        mapUniversity(it)
    }