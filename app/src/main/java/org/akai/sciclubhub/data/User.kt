package org.akai.sciclubhub.data

data class User(
    val uuid: UUID,
    val username: String,
    val email: String?,
    val name: String?,
    val surname: String?,
    val uniList: List<UUID>
)
