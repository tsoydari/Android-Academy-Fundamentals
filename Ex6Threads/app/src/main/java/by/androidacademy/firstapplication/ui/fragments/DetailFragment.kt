package by.androidacademy.firstapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import coil.api.load
import kotlinx.android.synthetic.main.fragment_details.*

class DetailFragment : Fragment(R.layout.fragment_details) {

    private val movieDetail: Movie by lazy { DetailFragmentArgs.fromBundle(requireArguments()).movieDetail }
    private val detailViewModel: DetailViewModel
            by viewModels {
                DetailViewModelFactory(Dependencies.moviesRepository, movieDetail, requireActivity().application)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
        initListeners()
    }

    private fun initUI() {
        movieDetail.apply {
            ivDetailsBack.load(backdropUrl)
            ivDetailsImage.load(posterUrl)
            tvDetailsTitle.text = title
            tvDetailsReleasedDate.text = releaseDate
            tvDetailsOverviewText.text = overview
        }
    }

    private fun initObservers() {
        detailViewModel.trailerUrl.observe(viewLifecycleOwner, Observer { url ->
            openMovieTrailer(url)
        })

        detailViewModel.errorEvent.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListeners() {
        btnDetailsTrailer.setOnClickListener{
            detailViewModel.onTrailerButtonClicked()
        }
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
