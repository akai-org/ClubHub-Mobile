package org.akai.sciclubhub.data.member

import org.akai.sciclubhub.data.UUID

data class Member(
    val uuid: UUID,
    val role: MemberRole
)
