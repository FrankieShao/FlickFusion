package org.real.flickfusion.repo

import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: auth repository
 */
interface AuthRepo {
    fun login(name: String, psd: String): Flow<Result<Boolean>>
    fun logout(): Flow<Result<Boolean>>
}

