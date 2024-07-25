package org.akai.sciclubhub.ktor.response

import kotlinx.serialization.Serializable

@Serializable
data class Authorized(
    val uuid: String,
    val token: String,
)
