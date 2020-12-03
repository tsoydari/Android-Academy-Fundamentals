package by.androidacademy.firstapplication.ui.fragments.poster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.ui.activities.MoviesActivity
import coil.api.load
import kotlinx.android.synthetic.main.fragment_poster.*
import java.io.File

class PosterFragment : Fragment(R.layout.fragment_poster) {

    private val posterPath by lazy {
        PosterFragmentArgs.fromBundle(requireArguments()).pathPoster
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        val posterPath = intent.getStringExtra(POSTER_PATH)
        ivPoster.load(File(posterPath))
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
//        android.R.id.home -> {
//            finish()
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }

    companion object {

        private const val POSTER_PATH = "POSTER_PATH"

        fun newIntent(context: Context, posterPath: String): Intent {
            return Intent(context, MoviesActivity::class.java)
                    .putExtra(POSTER_PATH, posterPath)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}