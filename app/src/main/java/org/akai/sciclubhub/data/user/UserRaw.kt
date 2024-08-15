package org.akai.sciclubhub.data.user

data class UserRaw (
    val uuid: String,
    val username: String,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val uniList: List<String> = listOf(),
)