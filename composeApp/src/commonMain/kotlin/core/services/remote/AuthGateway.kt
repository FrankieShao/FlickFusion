package core.services.remote

import kotlinx.coroutines.flow.Flow

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