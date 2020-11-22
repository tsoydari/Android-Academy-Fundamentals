package by.androidacademy.firstapplication.ui.activities

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R

class MoviesActivity : AppCompatActivity(R.layout.activity_movies) {

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
}