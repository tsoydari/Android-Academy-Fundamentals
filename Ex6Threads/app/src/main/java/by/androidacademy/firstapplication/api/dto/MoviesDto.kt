package by.androidacademy.firstapplication.api.dto

import by.androidacademy.firstapplication.data.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String
)

@JsonClass(generateAdapter = true)
data class MovieVideoDto(
    val key: String
)

@JsonClass(generateAdapter = true)
data class MovieVideosDto(
    val results: List<MovieVideoDto>
)

@JsonClass(generateAdapter = true)
data class PopularMoviesDto(
    val results: List<MovieDto>
)

private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="

fun PopularMoviesDto.toListMovie(): List<Movie> = results.mapIndexed { index, movieDto ->
    Movie(
        id = movieDto.id,
        title = movieDto.title,
        posterUrl = POSTER_BASE_URL + movieDto.posterPath,
        backdropUrl = BACKDROP_BASE_URL + movieDto.backdropPath,
        overview = movieDto.overview,
        releaseDate = movieDto.releaseDate,
        popularity = index
    )
}

fun MovieVideoDto.toString(): String = YOUTUBE_BASE_URL + key