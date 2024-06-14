package framework.network.remote

import com.real.network.API.AUTH.CREATE_SESSION
import com.real.network.API.AUTH.DELETE_SESSION
import com.real.network.API.AUTH.REQUEST_TOKEN
import com.real.network.API.AUTH.VALIDATE_TOKEN
import framework.network.get
import core.services.remote.AuthGateway
import framework.network.model.DeleteSessionResponse
import framework.network.model.RequestToken
import framework.network.model.SessionResponse
import framework.network.model.TokenValid
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: Gateway for Auth
 */
class AuthGatewayImpl    constructor(
    private val httpClient: HttpClient
) : AuthGateway {

    override fun requestToken(): Flow<Result<String>> =
        get<RequestToken>(
            client = httpClient,
            url = REQUEST_TOKEN,
        ).map { result ->
            result.map { it.requestToken }
        }

    override fun validateToken(name: String, psd: String, token: String): Flow<Result<String>> =
        flow {
            val response: TokenValid = httpClient.post(VALIDATE_TOKEN) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("username" to name, "password" to psd, "request_token" to token))
            }.body()
            emit(Result.success(response.requestToken))
        }.catch {
            emit(Result.failure(it))
        }

    override fun createSession(token: String): Flow<Result<String>> =
        flow {
            val response: SessionResponse = httpClient.post(CREATE_SESSION) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("request_token" to token))
            }.body()
            emit(Result.success(response.sessionId))
        }.catch {
            emit(Result.failure(it))
        }

    override fun deleteSession(session: String): Flow<Result<Boolean>> =
        flow {
            val response: DeleteSessionResponse = httpClient.request(DELETE_SESSION) {
                method = io.ktor.http.HttpMethod.Delete
                contentType(ContentType.Application.Json)
                setBody(mapOf("session_id" to session))
            }.body()
            emit(Result.success(response.success))
        }.catch {
            emit(Result.failure(it))
        }

}