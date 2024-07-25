package org.real.flickfusion.data

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.real.flickfusion.local.AccountLocalSource
import org.real.flickfusion.remote.AuthGateway
import org.real.flickfusion.repo.AuthRepoImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Frank Shao
 * @created 23/07/2024
 * Description:
 */
class AuthRepoTest {

    private val authGateway = mockk<AuthGateway>()
    private val accountLocalSource = mockk<AccountLocalSource>()
    private lateinit var authRepo: AuthRepoImpl

    @BeforeTest
    fun before() {
        authRepo = AuthRepoImpl(authGateway, accountLocalSource)
    }

    @Test
    fun `test login success`() = runTest {
        val name = "Frank"
        val psd = "123"
        val validToken = "123"
        val sessionId = "456"
        every { authGateway.requestToken() } returns flowOf(Result.success(validToken))
        every { authGateway.validateToken(name, psd, validToken) } returns flowOf(Result.success(sessionId))
        every { authGateway.createSession(sessionId) } returns flowOf(Result.success(sessionId))
        coEvery { accountLocalSource.saveSession(sessionId) } returns Unit

        authRepo.login(name, psd).test {
            assert(awaitItem().isSuccess)
            awaitComplete()
        }
    }

    @Test
    fun `test login fail, when request token fail`() = runTest {
        val name = "Frank"
        val psd = "123"
        every { authGateway.requestToken() } returns flowOf(Result.failure(Exception("Failed to get token")))

        authRepo.login(name, psd).test {
            assertEquals("Failed to get token", awaitItem().exceptionOrNull()?.message)
            awaitComplete()
        }
    }

    @Test
    fun `test login fail, when valid token fail`() = runTest {
        val name = "Frank"
        val psd = "123"
        every { authGateway.requestToken() } returns flowOf(Result.success("faaf"))
        every { authGateway.validateToken(name, psd, "faaf") } returns flowOf(Result.failure(Exception("invalid token")))

        authRepo.login(name, psd).test {
            assertEquals("invalid token", awaitItem().exceptionOrNull()?.message)
            awaitComplete()
        }
    }
}