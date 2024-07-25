package org.akai.sciclubhub.ktor

import android.content.res.Resources
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import org.akai.sciclubhub.R
import org.akai.sciclubhub.data.Event
import org.akai.sciclubhub.data.EventOrganiserType
import org.akai.sciclubhub.data.EventRaw
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.University
import org.akai.sciclubhub.data.UniversityRaw
import org.akai.sciclubhub.data.User
import org.akai.sciclubhub.data.UserRaw
import org.akai.sciclubhub.ktor.response.Authorized
import java.time.Instant

object ClubHubClient {
    private const val TIME_OUT = 60_000
    private val client = HttpClient(Android) {
        expectSuccess = true

        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d(
                    "HTTP Response",
                    "status:${response.status.value} content:${response.receive<String>()}"
                )
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
    suspend fun authorize(email: String, password: String): Authorized =
        client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.login_path))
            }
            body = mapOf(
                "email" to email,
                "password" to password
            )
        }

    suspend fun registerNewUser(password: String, email: String, username: String): Authorized =
        client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.register_path))
            }
            body = mapOf(
                "email" to email,
                "password" to password,
                "username" to username
            )
        }

    suspend fun getUserInfo(searchedUserUUID: UUID, authorization: Authorized): User {
        val response: UserRaw = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(
                    Resources.getSystem().getString(R.string.account_path),
                    searchedUserUUID.value
                )
            }
            header(HttpHeaders.Authorization, authorization.token)
        }

        return User(
            uuid = UUID(response.uuid),
            username = response.username,
            email = response.email,
            name = response.name,
            surname = response.surname,
            uniList = response.uniList.map { UUID(it) },
        )
    }

    suspend fun getUniversities(): List<University> {
        val response: List<UniversityRaw> = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.universities_path))
            }
        }

        return response.map {
            University(
                uuid = UUID(it.uuid),
                name = it.name,
                city = it.city,
                shortName = it.shortName,
            )
        }
    }

    suspend fun editUserInfo(authorization: Authorized, user: User): User {
        val response: UserRaw = client.put {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(
                    Resources.getSystem().getString(R.string.account_path),
                    user.uuid.value,
                    "edit"
                )
            }
            header(HttpHeaders.Authorization, authorization.token)
            body = mapOf(
                "username" to user.username,
                "name" to user.name,
                "surname" to user.surname,
                "uniList" to user.uniList.map { it.value }
            )
        }

        return User(
            uuid = UUID(response.uuid),
            username = response.username,
            email = response.email,
            name = response.name,
            surname = response.surname,
            uniList = response.uniList.map { UUID(it) },
        )
    }

    suspend fun getNonClubEvents(authorization: Authorized): List<Event> {
        val response: List<EventRaw> = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.events_path))
            }
            header(HttpHeaders.Authorization, authorization.token)
        }

        return response.map {
            Event(
                uuid = UUID(it.uuid),
                title = it.title,
                description = it.description,
                timestamp = Instant.ofEpochMilli(it.timestamp),
                location = it.location,
                open = it.open,
                participants = it.participants.map { uuid -> UUID(uuid) },
                organisers = it.organisers,
            )
        }
    }

    suspend fun postNewNonClubEvent(authorization: Authorized, event: Event): Event {
        val response: EventRaw = client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.events_path))
            }
            header(HttpHeaders.Authorization, authorization.token)
            body = mapOf(
                "title" to event.title,
                "description" to event.description,
                "timestamp" to event.timestamp.toEpochMilli(),
                "location" to event.location,
                "open" to event.open,
                "participants" to event.participants.map { it.value },
                "organisers" to event.organisers,
            )
        }

        return Event(
            uuid = UUID(response.uuid),
            title = response.title,
            description = response.description,
            timestamp = Instant.ofEpochMilli(response.timestamp),
            location = response.location,
            open = response.open,
            participants = response.participants.map { UUID(it) },
            organisers = response.organisers,
        )
    }

    suspend fun updateNonClubEvent(
        authorization: Authorized,
        uuid: UUID,
        title: String? = null,
        description: String? = null,
        timestamp: Instant? = null,
        location: String? = null,
        open: Boolean? = null,
        participants: List<UUID>? = null,
    ): Event {
        val requestBody = mutableMapOf<String, Any?>(
            "uuid" to uuid.value
        )
        title?.let { requestBody["title"] = it }
        description?.let { requestBody["description"] = it }
        timestamp?.let { requestBody["timestamp"] = it.toEpochMilli() }
        location?.let { requestBody["location"] = it }
        open?.let { requestBody["open"] = it }
        participants?.let { requestBody["participants"] = it.map { uuidP -> uuidP.value } }

        val response: EventRaw = client.put {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(Resources.getSystem().getString(R.string.events_path))
            }
            header(HttpHeaders.Authorization, authorization.token)
            body = requestBody
        }

        return Event(
            uuid = UUID(response.uuid),
            title = response.title,
            description = response.description,
            timestamp = Instant.ofEpochMilli(response.timestamp),
            location = response.location,
            open = response.open,
            participants = response.participants.map { UUID(it) },
            organisers = response.organisers,
        )
    }

    suspend fun getOrganiserTypes(): List<EventOrganiserType> =
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(
                    Resources.getSystem().getString(R.string.events_path),
                    Resources.getSystem().getString(R.string.organiser_types_path)
                )
            }
        }

    suspend fun joinNonClubEvent(authorization: Authorized, eventUUID: UUID): Event {
        val response: EventRaw = client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = Resources.getSystem().getString(R.string.api_clubhub_domain)
                path(
                    Resources.getSystem().getString(R.string.events_path),
                    Resources.getSystem().getString(R.string.join_path)
                )
            }
            header(HttpHeaders.Authorization, authorization.token)
            body = mapOf(
                "uuid" to eventUUID.value
            )
        }

        return Event(
            uuid = UUID(response.uuid),
            title = response.title,
            description = response.description,
            timestamp = Instant.ofEpochMilli(response.timestamp),
            location = response.location,
            open = response.open,
            participants = response.participants.map { UUID(it) },
            organisers = response.organisers,
        )

    }
}



