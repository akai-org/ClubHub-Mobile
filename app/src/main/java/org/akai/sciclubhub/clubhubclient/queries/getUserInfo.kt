package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.accountPath
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.user.User
import org.akai.sciclubhub.data.user.UserRaw

suspend fun getUserInfo(authorization: Authorized, searchedUserUUID: UUID) =
    client.get<UserRaw> {
        url {
            path(
                accountPath,
                searchedUserUUID.value
            )
        }
        header(HttpHeaders.Authorization, authorization.token)
    }.let {
        User(
            uuid = UUID(it.uuid),
            username = it.username,
            email = it.email,
            name = it.name,
            surname = it.surname,
            uniList = it.uniList.map { uniUUID -> getUniversity(UUID(uniUUID)) },
        )
    }