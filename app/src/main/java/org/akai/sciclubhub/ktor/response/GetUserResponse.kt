package org.akai.sciclubhub.ktor.response

import kotlinx.serialization.Serializable
import org.akai.sciclubhub.user.User

@Serializable
data class GetUserResponse (
    val success: Boolean,
    val message: String,
    val user: User
)