package org.real.flickfusion.repo

import org.real.flickfusion.remote.GenreGateway
/**
 * @author Frank Shao
 * @created 20/07/2024
 * Description:
 */
class GenreRepoImpl(private val gateway: GenreGateway) : GenreRepo {
    override fun getMovieGenres() = gateway.getMovieGenres()
    override fun getTVGenres() = gateway.getTVGenres()
}