package framework.network.remote

import com.real.network.API
import framework.network.get
import core.domain.model.Genre
import core.services.remote.GenreGateway
import framework.network.model.Genres
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Gateway for Genres
 */
class GenreGatewayImpl    constructor(
    private val client: HttpClient
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