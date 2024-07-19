package org.akai.sciclubhub.user

data class User (
    val email: String,
    val username: String,
    val membership: List<String>
)