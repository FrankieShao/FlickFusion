package org.real.flickfusion.test

import org.real.flickfusion.model.Configure
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.MediaModel
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.MovieDetail
import org.real.flickfusion.model.TVShow
import org.real.flickfusion.model.TVShowDetail

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */

fun fakeMoviesPage1(): List<Movie> {
    return TestDataLoader.loadFromJson("test_data/movies1.json")
}

fun fakeMoviesPage2(): List<Movie> {
    return TestDataLoader.loadFromJson("test_data/movies2.json")
}

fun fakeTVsPage1(): List<TVShow> {
    return TestDataLoader.loadFromJson("test_data/tvs1.json")
}

fun fakeTVsPage2(): List<TVShow> {
    return TestDataLoader.loadFromJson("test_data/tvs2.json")
}

fun fakeMediaModel() = MediaModel(
    id = 1022789,
    title = "Inside Out 2",
    posterUrl = "https://image.tmdb.org/t/p/w500/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
    voteAverage = 8.0,
    releaseDate = "2024-06-11",
    type = MediaType.MOVIE,
    overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.\"",
    backdropPath = "https://image.tmdb.org/t/p/original/xg27NrXi7VXCGUr7MG75UqLl6Vg.jpg",
    country = "US",
    genres = listOf("Animation", "Adventure"),
    cast = emptyList(),
)

fun fakeConfigure(): Configure {
    return TestDataLoader.loadFromJson<Configure>("test_data/configure.json")
}

fun fakeMovieDetail(): MovieDetail {
    return TestDataLoader.loadFromJson("test_data/movie_detail.json")
}

fun fakeTVDetail(): TVShowDetail {
    return TestDataLoader.loadFromJson("test_data/tv_detail.json")
}

fun fakeCredits(): Credits {
    return TestDataLoader.loadFromJson("test_data/credits.json")
}
