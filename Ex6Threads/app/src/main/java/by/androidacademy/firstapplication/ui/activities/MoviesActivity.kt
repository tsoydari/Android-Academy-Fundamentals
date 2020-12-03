package by.androidacademy.firstapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.androidservices.downloads.DownloadService.Companion.POSTER_PATH
import by.androidacademy.firstapplication.ui.fragments.detail.DetailFragmentDirections

class MoviesActivity : AppCompatActivity(R.layout.activity_movies) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        checkStringIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkStringIntent()
    }

    private fun checkStringIntent() {
        val posterPath = intent.getStringExtra(POSTER_PATH)
        posterPath?.run {
            findNavController(R.id.nav_host_fragment)
                    .navigate(DetailFragmentDirections.actionDetailsFragmentToPosterFragment(this))
        }
    }
}