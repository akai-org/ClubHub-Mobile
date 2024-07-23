package org.akai.sciclubhub.ktor

import android.content.res.AssetManager
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
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import org.akai.sciclubhub.ktor.response.GetUserResponse
import org.akai.sciclubhub.ktor.response.LoginResponse
import org.akai.sciclubhub.ktor.response.RegisterResponse
import org.akai.sciclubhub.user.User
import java.util.Properties

private const val TIME_OUT = 60_000
object KtorClient {
    private var client: HttpClient? = null
    private val apiProperties: Properties = Properties()
    fun getClient(assets: AssetManager): HttpClient {
        if (apiProperties.isEmpty)
            apiProperties.load(assets.open("clubhub_api.properties"))


        return client ?: synchronized(this) {
            client ?: HttpClient(Android) {
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
                        Log.d("HTTP status:", "${response.status.value}")
                    }
                }

                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
                .also { client = it }
        }
    }
    suspend fun HttpClient.login(email: String, password: String): String?  {
//        TODO("Unify return type")
        return "temporaryToken1234567890"
        try {
            val response: HttpResponse = post(
                urlString = "${pathBegin()}/${apiProperties.getProperty("subdirectory.login")}"
            ) {
                body = mapOf(
                    "email" to email,
                    "password" to password
                )
            }

            val loginResponse: LoginResponse = response.receive()
            return loginResponse.token
        } catch (e: Exception) {
            Log.e("KtorClient", e.message ?: "Something went wrong and I don't know what")
        }
        return null
    }

    suspend fun HttpClient.register(email: String, password: String, username: String, firstName: String = "", lastName: String = "", discord: String = ""): Boolean? {
        //TODO("Unify return type")
        try {
            val response: HttpResponse = post(
                urlString = "${pathBegin()}/${apiProperties.getProperty("subdirectory.register")}"
            ) {
                body = mapOf(
                    "email" to email,
                    "password" to password,
                    "username" to username,
                    "firstname" to firstName,
                    "lastname" to lastName,
                    "discord" to discord
                )
            }
            val registerResponse: RegisterResponse = response.receive()
            return registerResponse.success
        } catch (e: Exception) {
            Log.e("KtorClient", e.message ?: "Something went wrong and I don't know what")
        }
        return null
    }

    suspend fun HttpClient.getUser(username: String): User? {
//        TODO("Unify return type")
        try {
            val response: HttpResponse = post(
                urlString = "${pathBegin()}/$username"
            )
            val getUserResponse: GetUserResponse = response.receive()
            return getUserResponse.user
        }
        catch (e: Exception) {
            Log.e("KtorClient", e.message ?: "Something went wrong and I don't know what")
        }
        return null
    }

    private fun pathBegin(): String = "${
        apiProperties.getProperty("protocol")
    }://${
        apiProperties.getProperty("full_domain")
    }"
}



