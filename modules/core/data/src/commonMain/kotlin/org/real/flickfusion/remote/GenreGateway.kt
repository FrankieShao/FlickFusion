package org.real.flickfusion.remote

import org.real.flickfusion.model.Genres
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.model.Genre

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Gateway for Genres
 */
interface GenreGateway {
    fun getMovieGenres(): Flow<Result<List<Genre>>>
    fun getTVGenres(): Flow<Result<List<Genre>>>
}

class GenreGatewayImpl constructor(
    private val client: IHttp
) : GenreGateway {

    override fun getMovieGenres(): Flow<Result<List<Genre>>> =
        get<Genres>(client, API.GENRE.MOVIE).map {
            Result.success(it.getOrNull()?.genres ?: emptyList())
        }


    override fun getTVGenres(): Flow<Result<List<Genre>>> =
        get<Genres>(client, API.GENRE.TV).map {
            Result.success(it.getOrNull()?.genres ?: emptyList())
        }
}