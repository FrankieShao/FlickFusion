package core.services.local

import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: local source for account
 */
interface AccountLocalSource {
     suspend fun saveAccountId(accountId: String)
     fun getAccountId(): Flow<String>

     suspend fun saveSession(session: String)
     fun getSession(): Flow<String>
}