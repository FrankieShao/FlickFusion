package org.real.flickfusion.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import service.IProperty

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

class AccountLocalSourceImpl(private val property: IProperty): AccountLocalSource {

     override suspend fun saveAccountId(accountId: String) {

     }

     override fun getAccountId(): Flow<String> =
          //todo need to be implemented with sqldelight
          flow {
               emit(property.accountId())
          }

     override suspend fun saveSession(session: String) {
     }

     override fun getSession() =  flow {
          emit("")
     }
}