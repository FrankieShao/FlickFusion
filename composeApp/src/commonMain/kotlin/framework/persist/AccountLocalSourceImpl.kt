package framework.persist

import core.services.local.AccountLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.real.flickfusion.BuildKonfig

/**
 * @author Frank Shao
 * @created 09/06/2024
 * Description: data store for account.
 * todo need to be implemented with sqldelight
 */
class AccountLocalSourceImpl: AccountLocalSource {

    override suspend fun saveAccountId(accountId: String) {

    }

    override fun getAccountId(): Flow<String> =
        //todo need to be implemented with sqldelight
        flow {
            emit(BuildKonfig.accountId)
        }

    override suspend fun saveSession(session: String) {
    }

    override fun getSession() =  flow {
        emit("")
    }
}