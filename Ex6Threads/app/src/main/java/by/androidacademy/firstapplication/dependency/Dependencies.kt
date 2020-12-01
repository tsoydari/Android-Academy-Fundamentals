package by.androidacademy.firstapplication.dependency

import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import androidx.room.Room
import by.androidacademy.firstapplication.App
import by.androidacademy.firstapplication.androidservices.threads.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.threads.ServiceDelegate
import by.androidacademy.firstapplication.androidservices.threads.ServiceViewModelState
import by.androidacademy.firstapplication.androidservices.threads.WorkerParamsRequest
import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.db.AppDatabase
import by.androidacademy.firstapplication.repository.MoviesRepository
import by.androidacademy.firstapplication.utils.NotificationsManager
import by.androidacademy.firstapplication.utils.ResourceManager
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object Dependencies {

    private val db by lazy { createDataBase() }

    val moviesRepository by lazy {
        createMoviesRepository()
    }

    val heavyWorkManager by lazy {
        createHeavyWorkManager()
    }

    val serviceDelegate by lazy {
        createServiceDelegate()
    }

    val notificationsManager by lazy {
        createNotificationsManager()
    }

    val workerParamsRequest by lazy {
        createWorkerParamsRequest()
    }



    private fun createDataBase() = App.instance?.let {
        Room.databaseBuilder(
            it,
            AppDatabase::class.java,
            "movies.db"
        ).build()
    }

    private fun createHeavyWorkManager() = HeavyWorkerManager()

    private fun createServiceDelegate() = ServiceDelegate()

    private fun createNotificationsManager(): NotificationsManager? {
        return App.instance?.let { application ->
            NotificationsManager(
                    application,
                    ResourceManager(application),
                    application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            )
        }
    }

    private fun createWorkerParamsRequest() = WorkerParamsRequest()

    private fun createMoviesRepository(): MoviesRepository {
        return MoviesRepository(
            createTmdbServiceApi(),
            db?.movieDao(),
            db?.videoDao()
        )
    }

    private fun createTmdbServiceApi(): TmdbServiceApi {
        return createRetrofit().create()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun serviceViewModelState() = ServiceViewModelState()
}