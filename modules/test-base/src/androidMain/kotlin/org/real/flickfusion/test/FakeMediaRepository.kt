package org.real.flickfusion.test

import kotlinx.coroutines.flow.flowOf
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.MovieDetail
import org.real.flickfusion.model.TVShowDetail
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
class FakeMovieRepository : MovieRepo {

    override fun getTrendingMovies() = flowOf(Result.success(fakeMoviesPage1()))

    override fun getTopRatedMovies(page: Int) = flowOf(Result.success(fakeMoviesPage1()))

    override fun getPopularMovies(page: Int) = flowOf(Result.success(fakeMoviesPage1()))

    override fun getNowPlayingMovies(page: Int) =  flowOf(Result.success(fakeMoviesPage1()))

    override fun getUpcomingMovies(page: Int) = flowOf(Result.success(fakeMoviesPage1()))

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return fakeMovieDetail()
    }

    override suspend fun getMovieCredits(movieId: Int): Credits {
        return fakeCredits()
    }
}

class FakeTVRepository : TVRepo {
    override fun getTrendingTVShows() = flowOf(Result.success(fakeTVsPage1()))

    override fun getPopularTVShows(page: Int) = flowOf(Result.success(fakeTVsPage1()))

    override fun getTopRatedTVShows(page: Int) = flowOf(Result.success(fakeTVsPage1()))

    override fun getOnTheAirTVShows(page: Int) = flowOf(Result.success(fakeTVsPage1()))

    override fun getAiringTodayTVShows(page: Int) = flowOf(Result.success(fakeTVsPage1()))

    override suspend fun getTVShowDetail(tvShowId: Int): TVShowDetail {
        return fakeTVDetail()
    }

    override suspend fun getTVShowCredits(tvShowId: Int): Credits {
        return fakeCredits()
    }

}