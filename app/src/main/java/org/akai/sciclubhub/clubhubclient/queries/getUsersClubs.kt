package org.akai.sciclubhub.clubhubclient.queries

import io.ktor.client.request.get
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.client
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.club.Club
import org.akai.sciclubhub.data.club.ClubRaw
import org.akai.sciclubhub.data.member.mapMember

suspend fun getUsersClubs(authorized: Authorized, userUUID: UUID) =
    client.get<List<ClubRaw>> {
        url {
            path("account", userUUID.toString(), "clubs")
        }
    }.let {
        it.map { rawClub ->
            Club(
                uuid = UUID(rawClub.uuid),
                name = rawClub.name,
                open = rawClub.open,
                uniList = rawClub.uniList.map { universityUUID -> getUniversity(UUID(universityUUID)) },
                meetsList = rawClub.meetsList.map { meetingUUID -> getMeeting(UUID(meetingUUID)) },
                eventsList = rawClub.eventsList.map { eventUUID -> getEvent(authorized, UUID(eventUUID)) },
                projectsList = rawClub.projectsList.map { projectUUID -> UUID(projectUUID) },
                memberList = rawClub.memberList.map { rawMember -> mapMember(rawMember) },
            )
        }
    }




