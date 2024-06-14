package core.domain.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: This file contains the model for the TV show.
 */
@Serializable
data class TVShow(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
): Media

@Serializable
data class TVShowDetail(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("created_by")
    val createdBy: List<CreatedBy>,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,
    @SerialName("name")
    val name: String,
    @SerialName("networks")
    val networks: List<Network>,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("seasons")
    val seasons: List<Season>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("type")
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
) {
    @Serializable
    data class CreatedBy(
        @SerialName("credit_id")
        val creditId: String,
        @SerialName("gender")
        val gender: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("original_name")
        val originalName: String,
        @SerialName("profile_path")
        val profilePath: String? = null
    )



    @Serializable
    data class LastEpisodeToAir(
        @SerialName("air_date")
        val airDate: String? = null,
        @SerialName("episode_number")
        val episodeNumber: Int?,
        @SerialName("episode_type")
        val episodeType: String?,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String?,
        @SerialName("overview")
        val overview: String?,
        @SerialName("production_code")
        val productionCode: String?,
        @SerialName("runtime")
        val runtime: Int? = null,
        @SerialName("season_number")
        val seasonNumber: Int?,
        @SerialName("show_id")
        val showId: Int?,
        @SerialName("still_path")
        val stillPath: String?,
        @SerialName("vote_average")
        val voteAverage: Double?,
        @SerialName("vote_count")
        val voteCount: Int
    )

    @Serializable
    data class Network(
        @SerialName("id")
        val id: Int,
        @SerialName("logo_path")
        val logoPath: String? = null,
        @SerialName("name")
        val name: String,
        @SerialName("origin_country")
        val originCountry: String
    )

    @Serializable
    data class ProductionCompany(
        @SerialName("id")
        val id: Int,
        @SerialName("logo_path")
        val logoPath: String? = null,
        @SerialName("name")
        val name: String,
        @SerialName("origin_country")
        val originCountry: String
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String,
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class Season(
        @SerialName("air_date")
        val airDate: String? = null,
        @SerialName("episode_count")
        val episodeCount: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("overview")
        val overview: String? = null,
        @SerialName("poster_path")
        val posterPath: String? = null,
        @SerialName("season_number")
        val seasonNumber: Int,
        @SerialName("vote_average")
        val voteAverage: Double
    )

    @Serializable
    data class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String,
        @SerialName("iso_639_1")
        val iso6391: String,
        @SerialName("name")
        val name: String
    )
}