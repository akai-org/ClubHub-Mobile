package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.universitiesPath
import org.akai.sciclubhub.data.university.UniversityRaw
import org.akai.sciclubhub.data.university.mapUniversity

suspend fun getUniversities() =
    client.get<List<UniversityRaw>> {
        url {
            path(universitiesPath)
        }
    }.map (::mapUniversity)