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
        return when (item.itemId) {

            R.id.action_open_new_coroutine -> {
                startActivity(Intent(this, CoroutinesActivity::class.java))
                true
            }

            else ->
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
    }
}