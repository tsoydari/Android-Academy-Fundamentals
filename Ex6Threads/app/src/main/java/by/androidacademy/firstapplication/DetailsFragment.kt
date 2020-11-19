package by.androidacademy.firstapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.androidacademy.firstapplication.data.Movie

class DetailsFragment : Fragment(R.layout.fragment_details) {

    companion object {

        private const val ARGS_MOVIE = "ARGS_MOVIE"

        fun newInstance(movie: Movie): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_MOVIE, movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(ARGS_MOVIE)?.run {

            view.findViewById<ImageView>(R.id.ivDetailsBack).setImageResource(backdropRes)
            view.findViewById<ImageView>(R.id.ivDetailsImage).setImageResource(posterRes)
            view.findViewById<TextView>(R.id.tvDetailsTitle).text = title
            view.findViewById<TextView>(R.id.tvDetailsReleasedDate).text = releaseDate
            view.findViewById<TextView>(R.id.tvDetailsOverviewText).text = overview

            val movieButton: Button = view.findViewById(R.id.btnDetailsTrailer)
            movieButton.setOnClickListener {
                openMovieTrailer(trailerUrl)
            }
        }

    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }


}
