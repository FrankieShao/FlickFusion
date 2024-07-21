package org.real.flickfusion.repo

import org.real.flickfusion.model.AccountInfo
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.TVShow
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: account repository
 */
interface AccountRepo {
    fun getInfo(): Flow<Result<AccountInfo>>

    /***
     * Add favorite
     * @param account_id
     * {
     *     "media_type": "movie",
     *     "media_id": 550,
     *     "favorite": true
     * }
     */
    fun addFavorite(
        mediaType: String,
        mediaId: Int
    ): Flow<Result<Boolean>>

    fun getFavoriteMovie(page: Int): Flow<Result<List<Movie>>>

    fun getFavoriteTV(page: Int): Flow<Result<List<TVShow>>>
}

