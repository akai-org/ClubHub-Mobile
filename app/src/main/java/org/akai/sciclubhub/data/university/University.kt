package org.akai.sciclubhub.data.university

import org.akai.sciclubhub.data.UUID

data class University(
    val uuid: UUID,
    val name: String,
    val city: String,
    val shortName: String
)
