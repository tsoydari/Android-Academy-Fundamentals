package by.androidacademy.firstapplication.dependency

import androidx.room.Room
import by.androidacademy.firstapplication.App
import by.androidacademy.firstapplication.androidservices.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.ServiceViewModelState
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest
import by.androidacademy.firstapplication.api.TmdbServiceApi
import by.androidacademy.firstapplication.db.AppDatabase
import by.androidacademy.firstapplication.repository.MoviesRepository
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