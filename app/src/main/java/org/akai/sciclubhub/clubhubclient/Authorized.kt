package org.akai.sciclubhub.clubhubclient

import org.akai.sciclubhub.data.UUID

data class Authorized(
    val uuid: UUID,
    val token: String,
)
