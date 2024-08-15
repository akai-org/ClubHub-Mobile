package org.akai.sciclubhub.data.user

import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.university.University

fun User.Companion.DummyUser() = User(
    uuid = UUID("1213"),
    name = "Mike",
    username = "nan.weber",
    surname = "Moen",
    email = "william.henry.harrison@example-pet-store.com",
    uniList = listOf(
        University(
        uuid = UUID("1213"),
        name = "University of Toronto",
        city = "Toronto",
        shortName = "UOT",
    )
    )
)