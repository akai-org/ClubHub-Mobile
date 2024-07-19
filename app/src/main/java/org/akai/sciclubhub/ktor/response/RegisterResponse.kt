package org.akai.sciclubhub.ktor.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val duplicate: Boolean,
    val duplicatedFields: List<String>,
    val message: String,
    val error: Boolean
)
