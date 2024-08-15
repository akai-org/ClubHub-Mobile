package org.akai.sciclubhub.data.member

import org.akai.sciclubhub.data.UUID

data class MemberRaw (
    val uuid: String,
    val role: String,
)

fun mapMember(member: MemberRaw) =
    Member(
        uuid = UUID(member.uuid),
        role = when(member.role) {
            MemberRole.Admin.name -> MemberRole.Admin
            MemberRole.Member.name -> MemberRole.Member
            MemberRole.Moderator.name -> MemberRole.Moderator
            else -> throw IllegalArgumentException("Invalid member role: ${member.role}")
        },
    )