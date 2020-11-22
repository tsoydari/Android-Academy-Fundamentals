package by.androidacademy.firstapplication.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.data.Movie

class MoviesViewModel: ViewModel() {
    val navigateToSelectedProperty = MutableLiveData<Movie>()

    fun displayPropertyDetails(movieProperty: Movie) {
        navigateToSelectedProperty.postValue(movieProperty)
    }

    fun displayPropertyDetailsComplete() {
        navigateToSelectedProperty.postValue(null)
    }

}