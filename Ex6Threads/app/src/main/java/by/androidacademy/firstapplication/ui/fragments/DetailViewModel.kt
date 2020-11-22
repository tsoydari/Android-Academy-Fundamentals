package by.androidacademy.firstapplication.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.data.Movie

class DetailViewModel(private val movieDetail: Movie): ViewModel() {

    val movie = MutableLiveData<Movie>(movieDetail)

}