package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.accountPath
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.clubhubclient.editPath
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.user.User
import org.akai.sciclubhub.data.user.UserRaw

suspend fun editUserInfo(authorization: Authorized, user: User) =
    client.put<UserRaw> {
        url {
            path(
                accountPath,
                user.uuid.value,
                editPath
            )
        }
        header(HttpHeaders.Authorization, authorization.token)
        body = mapOf(
            "username" to user.username,
            "name" to user.name,
            "surname" to user.surname,
            "uniList" to user.uniList.map { it.uuid.value }
        )
    }.let {
        User(
            uuid = UUID(it.uuid),
            username = it.username,
            email = it.email,
            name = it.name,
            surname = it.surname,
            uniList = it.uniList.map {uniUUID -> getUniversity(UUID(uniUUID)) },
        )
    }