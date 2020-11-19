package by.androidacademy.firstapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.androidacademy.firstapplication.adapters.MoviesAdapter
import by.androidacademy.firstapplication.data.DataStorage
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.threads.CoroutineActivity
import by.androidacademy.firstapplication.threads.ThreadsActivity
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(R.layout.activity_movies) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movies = DataStorage.getMoviesList()
        val adapter = MoviesAdapter(this, movies) { position ->
            showDetailsFragment(movies, position)
        }

        rvMoviesList.adapter = adapter

        val decoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        decoration.setDrawable(ContextCompat.getDrawable(this, R.color.grey)!!)
        rvMoviesList.addItemDecoration(decoration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_coroutine -> {
                // Open Async Task Activity
                startActivity(Intent(this, CoroutineActivity::class.java))
                return true
            }

            R.id.action_open_thread_handler -> {
                // Open Thread Handler Activity
                startActivity(Intent(this, ThreadsActivity::class.java))
                return true
            }

            else ->
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }

    private fun showDetailsFragment(
        movies: List<Movie>,
        position: Int
    ) {
        val detailsFragment = DetailsGalleryFragment.newInstance(movies, position)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.clContainer, detailsFragment)
            .commit()
    }
}