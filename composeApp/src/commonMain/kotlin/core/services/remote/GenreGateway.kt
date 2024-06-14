package core.services.remote

import core.domain.model.Genre
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Gateway for Genre
 */
interface GenreGateway {
    fun getMovieGenres(): Flow<Result<List<Genre>>>
    fun getTVGenres(): Flow<Result<List<Genre>>>
}