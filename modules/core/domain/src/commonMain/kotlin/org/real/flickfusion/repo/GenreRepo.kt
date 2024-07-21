package org.real.flickfusion.repo

import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.model.Genre

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Gateway for Genre
 */
interface GenreRepo {
    fun getMovieGenres(): Flow<Result<List<Genre>>>
    fun getTVGenres(): Flow<Result<List<Genre>>>
}