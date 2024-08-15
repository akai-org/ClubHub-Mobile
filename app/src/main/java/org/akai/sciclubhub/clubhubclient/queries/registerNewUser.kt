package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.post
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.AuthorizedRaw
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.registerPath
import org.akai.sciclubhub.data.UUID

suspend fun registerNewUser(password: String, email: String, username: String) =
    client.post<AuthorizedRaw> {
        url {
            path(registerPath)
        }
        body = mapOf(
            "email" to email,
            "password" to password,
            "username" to username
        )
    }.let {
        Authorized(
            uuid = UUID(it.uuid),
            token = it.token
        )
    }