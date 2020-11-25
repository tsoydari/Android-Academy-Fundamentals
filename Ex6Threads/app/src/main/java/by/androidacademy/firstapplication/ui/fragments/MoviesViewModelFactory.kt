package by.androidacademy.firstapplication.ui.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.androidacademy.firstapplication.repository.MoviesRepository

class MoviesViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(moviesRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}