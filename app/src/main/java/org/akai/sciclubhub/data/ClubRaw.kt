package org.akai.sciclubhub.data

data class ClubRaw(
    val uuid: String,
    val name: String,
    val open: Boolean,
    val uniList: List<String>,
    val meetsList: List<String>,
    val eventsList: List<String>,
    val projectsList: List<String>,
    val memberList: List<MemberRaw>
)
