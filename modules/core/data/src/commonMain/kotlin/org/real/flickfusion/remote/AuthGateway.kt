package org.real.flickfusion.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.IHttp
import org.real.flickfusion.API.AUTH.CREATE_SESSION
import org.real.flickfusion.API.AUTH.DELETE_SESSION
import org.real.flickfusion.API.AUTH.REQUEST_TOKEN
import org.real.flickfusion.API.AUTH.VALIDATE_TOKEN
import org.real.flickfusion.model.DeleteSessionResponse
import org.real.flickfusion.model.RequestToken
import org.real.flickfusion.model.SessionResponse
import org.real.flickfusion.model.TokenValid

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: Gateway for Auth
 */
interface AuthGateway {
    fun requestToken(): Flow<Result<String>>
    fun validateToken(name: String, psd: String, token: String): Flow<Result<String>>
    fun createSession(token: String): Flow<Result<String>>
    fun deleteSession(session: String): Flow<Result<Boolean>>
}

class AuthGatewayImpl constructor(
    private val httpClient: IHttp
) : AuthGateway {

    override fun requestToken(): Flow<Result<String>> =
        get<RequestToken>(
            client = httpClient,
            url = REQUEST_TOKEN,
        ).map { result ->
            result.map { it.requestToken }
        }

    override fun validateToken(name: String, psd: String, token: String): Flow<Result<String>> =
        post<TokenValid>(
            client = httpClient,
            url = VALIDATE_TOKEN,
            body = mapOf("username" to name, "password" to psd, "request_token" to token)
        ).map { result ->
            result.map { it.requestToken }
        }

    override fun createSession(token: String): Flow<Result<String>> =
        post<SessionResponse>(
            client = httpClient,
            url = CREATE_SESSION,
            body = mapOf("request_token" to token)
        ).map { result ->
            result.map { it.sessionId }
        }

    override fun deleteSession(session: String): Flow<Result<Boolean>> =
        post<DeleteSessionResponse>(
            client = httpClient,
            url = DELETE_SESSION,
            body = mapOf("session_id" to session)
        ).map { result ->
            result.map { it.success }
        }

}