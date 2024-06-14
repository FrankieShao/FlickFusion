package framework.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.real.flickfusion.BuildKonfig

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: Ktor HTTP Client
 */
val CLIENT = HttpClient {
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 2)
    }
    install(Auth) {
        bearer {
            loadTokens {
                BearerTokens(
                    accessToken = BuildKonfig.apiKey,
                    refreshToken = ""
                )
            }
        }
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
}
