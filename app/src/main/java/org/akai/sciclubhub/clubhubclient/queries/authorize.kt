package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.post
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.AuthorizedRaw
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.loginPath
import org.akai.sciclubhub.data.UUID

suspend fun authorize(email: String, password: String) =
    client.post<AuthorizedRaw> {
        url {
            path(loginPath)
        }
        body = mapOf(
            "email" to email,
            "password" to password
        )
    }.let {
        Authorized(
            uuid = UUID(it.uuid),
            token = it.token
        )
    }