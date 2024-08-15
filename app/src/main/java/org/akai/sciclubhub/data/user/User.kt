package org.akai.sciclubhub.data.user

import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.university.University

data class User(
    val uuid: UUID,
    val username: String,
    val email: String?,
    val name: String?,
    val surname: String?,
    val uniList: List<University>
) {
    companion object
}
