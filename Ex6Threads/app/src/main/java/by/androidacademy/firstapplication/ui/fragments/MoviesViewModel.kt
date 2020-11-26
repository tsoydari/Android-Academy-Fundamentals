package by.androidacademy.firstapplication.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import by.androidacademy.firstapplication.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(
    app: Application
) : AndroidViewModel(app) {

    val navigateToSelectedProperty = MutableLiveData<Movie>()
    val isProgressBarVisibleMutableLiveData = MutableLiveData<Boolean>()
    val errorMutableLiveData = MutableLiveData<String>()
    val movies = MutableLiveData<List<Movie>>()
    val adapter by lazy { initAdapter() }

    init {
        viewModelScope.launch {
            try {
                isProgressBarVisibleMutableLiveData.postValue(true)

                val cachedMovies = withContext(Dispatchers.Default) {
                    Dependencies.moviesRepository.getCachedPopularMovies()
                }
                if (cachedMovies.isNotEmpty()) {
                    movies.postValue(cachedMovies)
                    isProgressBarVisibleMutableLiveData.postValue(false)
                }

                try {
                    val moviesUpdate = withContext(Dispatchers.IO) { Dependencies.moviesRepository.getPopularMovies() }
                    movies.postValue(moviesUpdate)
                } catch (error: Throwable) {
                    errorMutableLiveData.postValue(app.getString(R.string.error_load_movies_no_network))
                }

            } catch (error: Throwable) {
                errorMutableLiveData.postValue(
                    app.getString(
                        R.string.error_load_movies,
                        error.message ?: ""
                    )
                )
            } finally {
                isProgressBarVisibleMutableLiveData.postValue(false)
            }
        }
    }

    private fun initAdapter() = MoviesAdapter(movies.value ?: emptyList() ) { position ->
        movies.value?.get(position)?.let { displayPropertyDetails(it) }
    }

    fun displayPropertyDetails(movieProperty: Movie) {
        navigateToSelectedProperty.postValue(movieProperty)
    }

    fun displayPropertyDetailsComplete() {
        navigateToSelectedProperty.postValue(null)
    }
}