package by.androidacademy.firstapplication.ui.fragments.start

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.androidacademy.firstapplication.R
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val moviesViewModel: MoviesViewModel by viewModels {
        MoviesViewModelFactory(
            requireActivity().application
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMoviesList.adapter = moviesViewModel.adapter
        initObservers()
    }

    private fun initObservers() {
        moviesViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer { movie ->
            movie?.run {
                requireView().findNavController().navigate(
                    MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                        this
                    )
                )
                moviesViewModel.displayPropertyDetailsComplete()
            }
        })

        moviesViewModel.isProgressBarVisibleMutableLiveData.observe(viewLifecycleOwner, Observer { showProgress ->
            if (showProgress) {
                moviesProgressBar.visibility = View.VISIBLE
            }
            else {
                moviesProgressBar.visibility = View.GONE
            }
        })

        moviesViewModel.errorMutableLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        moviesViewModel.movies.observe(viewLifecycleOwner, Observer { updateMovies ->
            moviesViewModel.adapter.movies = updateMovies
            moviesViewModel.adapter.notifyDataSetChanged()
        })
    }
}