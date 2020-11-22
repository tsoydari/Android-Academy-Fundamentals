package by.androidacademy.firstapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import kotlinx.android.synthetic.main.fragment_details.*

class DetailFragment : Fragment(R.layout.fragment_details) {

    private val movieDetail: Movie by lazy { DetailFragmentArgs.fromBundle(requireArguments()).movieDetail }
    private val detailViewModel: DetailViewModel by viewModels {DetailViewModelFactory(movieDetail)}
    private var url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        detailViewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            movie?.run{
                ivDetailsBack.setImageResource(backdropRes)
                ivDetailsImage.setImageResource(posterRes)
                tvDetailsTitle.text = title
                tvDetailsReleasedDate.text = releaseDate
                tvDetailsOverviewText.text = overview
                url = trailerUrl
            }
        })
    }

    private fun initListeners() {
        btnDetailsTrailer.setOnClickListener{
            openMovieTrailer(url)
        }
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
