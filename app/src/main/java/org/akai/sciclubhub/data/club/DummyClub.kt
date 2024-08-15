package org.akai.sciclubhub.data.club

import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.university.dummyUniversity

val dummyClub = Club(
    uuid = UUID.randomUUID(),
    name = "Dummy Club",
    open = true,
    uniList = listOf(dummyUniversity),
    meetsList = emptyList(),
    eventsList = emptyList(),
    projectsList = emptyList(),
    memberList = emptyList(),
    )
