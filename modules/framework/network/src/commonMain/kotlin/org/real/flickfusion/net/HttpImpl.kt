package org.real.flickfusion.net

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import kotlinx.serialization.json.Json
import net.IHttp
import service.IProperty

/**
 * @author Frank Shao
 * @created 13/07/2024
 * Description:
 */
class HttpImpl(private val property: IProperty) : IHttp {

    private val CLIENT: HttpClient by lazy {
        HttpClient {
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = property.apiKey(),
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
    }

    override suspend fun <T> get(url: String, param: Map<String, String>, typeInfo: TypeInfo): T {
        return CLIENT.get(url) {
            url {
                param.forEach { (t, u) ->
                    parameters.append(t, u)
                }
            }
        }.body(typeInfo)
    }

    override suspend fun <T> post(
        url: String,
        param: Map<String, String>,
        body: Map<String, Any>,
        typeInfo: TypeInfo
    ): T {
        return CLIENT.post {
            url(url) {
                param.forEach { (t, u) ->
                    parameters.append(t, u)
                }
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body(typeInfo)
    }
}