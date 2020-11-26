package by.androidacademy.firstapplication.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.repository.MoviesRepository

class DetailViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val movieDetail: Movie,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                moviesRepository,
                movieDetail,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}