package org.akai.sciclubhub.data.university

import org.akai.sciclubhub.data.UUID

data class UniversityRaw(
    val uuid: String,
    val name: String,
    val city: String,
    val shortName: String
)

fun mapUniversity(universityRaw: UniversityRaw) = University(
    uuid = UUID(universityRaw.uuid),
    name = universityRaw.name,
    shortName = universityRaw.shortName,
    city = universityRaw.city,
)
