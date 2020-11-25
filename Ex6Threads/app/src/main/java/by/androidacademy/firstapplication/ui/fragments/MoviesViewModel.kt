package by.androidacademy.firstapplication.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
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

                val moviesFromRep = withContext(Dispatchers.IO) {
                    moviesRepository.getPopularMovies()
                }

                movies.postValue(moviesFromRep)
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

    fun displayPropertyDetails(movieProperty: Movie) {
        navigateToSelectedProperty.postValue(movieProperty)
    }

    fun displayPropertyDetailsComplete() {
        navigateToSelectedProperty.postValue(null)
    }

    private fun initAdapter() = MoviesAdapter(movies.value ?: emptyList() ) { position ->
        movies.value?.get(position)?.let { displayPropertyDetails(it) }
    }
}