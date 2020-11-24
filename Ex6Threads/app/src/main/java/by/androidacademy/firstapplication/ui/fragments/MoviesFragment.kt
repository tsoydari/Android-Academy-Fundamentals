package by.androidacademy.firstapplication.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.DataStorage
import by.androidacademy.firstapplication.ui.fragments.MoviesFragmentDirections.*
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    val moviesViewModel: MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movies = DataStorage.getMoviesList()
        val adapter = MoviesAdapter(movies) { position ->
            moviesViewModel.displayPropertyDetails(movies[position])
        }
        rvMoviesList.adapter = adapter
        initObservers()
    }

    private fun initObservers() {
        moviesViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer { movie ->
            movie?.run {
                requireView().findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(this))
                moviesViewModel.displayPropertyDetailsComplete()
            }
        })
    }
}