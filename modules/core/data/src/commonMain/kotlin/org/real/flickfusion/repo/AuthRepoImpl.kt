package org.real.flickfusion.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.real.flickfusion.local.AccountLocalSource
import org.real.flickfusion.remote.AuthGateway

/**
 * @author Frank Shao
 * @created 13/07/2024
 * Description: auth repository
 */
class AuthRepoImpl(
    private val authGateway: AuthGateway,
    private val accountLocalSource: AccountLocalSource
) : AuthRepo {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(name: String, psd: String): Flow<Result<Boolean>> =
        authGateway.requestToken()
            .flatMapConcat { tokenResult ->
                tokenResult.getOrNull()?.let { validResult ->
                    authGateway.validateToken(name, psd, validResult)
                } ?: flowOf(
                    Result.failure(IllegalStateException(tokenResult.exceptionOrNull()?.message))
                )
            }.flatMapConcat { validateResult ->
                validateResult.getOrNull()?.let { validResult ->
                    authGateway.createSession(validResult)
                } ?: flowOf(
                    Result.failure(IllegalStateException(validateResult.exceptionOrNull()?.message))
                )
            }.map { sessionResult ->
                (if (sessionResult.isSuccess) {
                    sessionResult.getOrNull()?.let {
                        accountLocalSource.saveSession(it)
                        Result.success(true)
                    } ?:
                    Result.failure(IllegalStateException("Create session failed"))
                } else {
                    Result.failure(IllegalStateException(sessionResult.exceptionOrNull()?.message))
                })
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