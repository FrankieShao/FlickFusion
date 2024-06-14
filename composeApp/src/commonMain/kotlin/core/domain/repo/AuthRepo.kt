package core.domain.repo

import core.services.local.AccountLocalSource
import core.services.remote.AuthGateway
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: auth repository
 */
interface AuthRepo {
    fun login(name: String, psd: String): Flow<Result<Boolean>>
    fun logout(): Flow<Result<Boolean>>
}

class AuthRepoImpl(
    private val authGateway: AuthGateway,
    private val accountLocalSource: AccountLocalSource
) : AuthRepo {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(name: String, psd: String): Flow<Result<Boolean>> =
        authGateway.requestToken()
            .flatMapConcat { tokenResult ->
                authGateway.validateToken(name, psd, tokenResult.getOrNull()!!)
            }.flatMapConcat { validateResult ->
                authGateway.createSession(validateResult.getOrNull()!!)
            }.map {
                accountLocalSource.saveSession(it.getOrNull()!!)
                Result.success(it.isSuccess)
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun logout(): Flow<Result<Boolean>> =
        accountLocalSource.getSession()
            .flatMapConcat {
                authGateway.deleteSession(it)
            }.map {
                accountLocalSource.saveSession("")
                accountLocalSource.saveAccountId("")
                Result.success(it.isSuccess)
            }

}