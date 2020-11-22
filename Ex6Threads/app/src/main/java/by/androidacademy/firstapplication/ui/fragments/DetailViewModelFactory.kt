package by.androidacademy.firstapplication.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.androidacademy.firstapplication.data.Movie

class DetailViewModelFactory(
    private val movieDetail: Movie
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieDetail) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}