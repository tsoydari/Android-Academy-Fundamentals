package by.androidacademy.firstapplication

import android.app.Application
import androidx.room.Room
import by.androidacademy.firstapplication.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "movies.db")
            .allowMainThreadQueries().build()
    }

    companion object {
        var db: AppDatabase? = null
        fun getDatabase(): AppDatabase? {
            return db
        }

    }
}