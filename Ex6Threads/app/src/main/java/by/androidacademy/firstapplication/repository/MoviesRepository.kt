package by.androidacademy.firstapplication.repository

import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.api.dto.toListMovie
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.data.Video
import by.androidacademy.firstapplication.db.MovieDao
import by.androidacademy.firstapplication.db.VideoDao


class MoviesRepository(
    private val tmdbServiceApi: TmdbServiceApi,
    private val movieDao: MovieDao?,
    private val videoDao: VideoDao?
) {

    suspend fun getPopularMovies(): List<Movie> {
        val popularMoviesDto = tmdbServiceApi.getPopularMovies()
        val movies = popularMoviesDto.toListMovie()

        movieDao?.deleteAll()
        movieDao?.insertAll(movies)

        return movies
    }

    suspend fun getMovieTrailerUrl(movie: Movie): String {
        val movieVideosDto = tmdbServiceApi.getMovieVideos(movie.id)
        val url = movieVideosDto.results.first().toString()

        videoDao?.insert(Video(movie.id, url))

        return url
    }

    fun getCachedPopularMovies(): List<Movie> {
        return movieDao?.getAll() ?: emptyList()
    }

    fun getCachedMovieTrailerUrl(movie: Movie): String? {
        val video = videoDao?.getVideoByMovieId(movie.id)
        return video?.url
    }

    fun deleteCachedData() {
        movieDao?.deleteAll()
        videoDao?.deleteAll()
    }
}