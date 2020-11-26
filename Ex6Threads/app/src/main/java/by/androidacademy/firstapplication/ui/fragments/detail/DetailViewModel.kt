package by.androidacademy.firstapplication.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.repository.MoviesRepository
import by.androidacademy.firstapplication.utils.SingleEventLiveData
import kotlinx.coroutines.launch

class DetailViewModel(private val moviesRepository: MoviesRepository,
                      private val movieDetail: Movie,
                      private val app: Application): ViewModel() {

    val trailerUrl = SingleEventLiveData<String>()
    val errorEvent = SingleEventLiveData<String>()

    fun onTrailerButtonClicked() {
        viewModelScope.launch {
            try {
                trailerUrl.postValue(moviesRepository.getMovieTrailerUrl(movieDetail))
            } catch (error: Throwable) {
                errorEvent.value = app.getString(
                    R.string.error_load_trailer,
                    error.message ?: ""
                )
            }
        }
    }
}