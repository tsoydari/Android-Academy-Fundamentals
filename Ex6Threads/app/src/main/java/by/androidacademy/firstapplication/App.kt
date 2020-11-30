package by.androidacademy.firstapplication

import android.app.Application
import androidx.room.Room
import by.androidacademy.firstapplication.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: App? = null
    }
}