package com.real.network

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: API endpoints
 */
object API {

    object MOVIE {
        const val TRENDING = "https://api.themoviedb.org/3/trending/movie/day"
        const val POPULAR = "https://api.themoviedb.org/3/movie/popular"
        const val TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated"
        const val NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing"
        const val UPCOMING = "https://api.themoviedb.org/3/movie/upcoming"
        const val DETAIL = "https://api.themoviedb.org/3/movie/"
        const val CREDITS = "https://api.themoviedb.org/3/movie/$/credits"
    }

    object TV {
        const val TRENDING = "https://api.themoviedb.org/3/trending/tv/day"
        const val POPULAR = "https://api.themoviedb.org/3/tv/popular"
        const val TOP_RATED = "https://api.themoviedb.org/3/tv/top_rated"
        const val ON_THE_AIR = "https://api.themoviedb.org/3/tv/on_the_air"
        const val AIRING_TODAY = "https://api.themoviedb.org/3/tv/airing_today"
        const val DETAIL = "https://api.themoviedb.org/3/tv/"
        const val CREDITS = "https://api.themoviedb.org/3/tv/$/credits"
    }

    object ACCOUNT {
        const val ACCOUNT = "https://api.themoviedb.org/3/account/"
        const val FAVORITE = "https://api.themoviedb.org/3/account/$/favorite"
        const val FAVORITE_MOVIE = "https://api.themoviedb.org/3/account/$/favorite/movies"
        const val FAVORITE_TV = "https://api.themoviedb.org/3/account/$/favorite/tv"
    }

    object AUTH {
        const val REQUEST_TOKEN = "https://api.themoviedb.org/3/authentication/token/new"
        const val VALIDATE_TOKEN = "https://api.themoviedb.org/3/authentication/token/validate_with_login"
        const val CREATE_SESSION = "https://api.themoviedb.org/3/authentication/session/new"
        const val DELETE_SESSION = "https://api.themoviedb.org/3/authentication/session"
    }

    object SEARCH {
        const val SEARCH = "https://api.themoviedb.org/3/search/multi"
    }

    object CONFIGURE {
        const val CONFIGURE = "https://api.themoviedb.org/3/configuration"
    }

    object GENRE {
        const val MOVIE = "https://api.themoviedb.org/3/genre/movie/list"
        const val TV = "https://api.themoviedb.org/3/genre/tv/list"
    }
}