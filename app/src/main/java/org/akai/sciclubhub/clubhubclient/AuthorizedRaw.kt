package org.akai.sciclubhub.clubhubclient

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizedRaw(
    val uuid: String,
    val token: String,
)
