package by.androidacademy.firstapplication.androidservices.threads

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

const val DELAY_TICK = 100L
const val MAX_PROGRESS = 100
const val DELAY_VALUE = DELAY_TICK * MAX_PROGRESS + 1000L//-1sec for cold start

class HeavyWorkerManager {

    private var handler: Handler = Handler()
    private var runnable: Runnable = initWork()
    private var counter = 0
    private val progressUpdaterService: MutableLiveData<Int> = MutableLiveData(0)

    private fun initWork() =
        Runnable {
            showProgressNumber(
                when {
                    counter < MAX_PROGRESS -> {
                        counter++
                    }
                    else -> {
                        counter
                    }
                }
            )
            handler.postDelayed(runnable, DELAY_TICK)
    }

    private fun showProgressNumber(progress: Int) {
        progressUpdaterService.postValue(progress)
    }

    fun startWork() {
        handler.post(runnable)
    }

    fun resetProgress() {
        counter = 0
        showProgressNumber(counter)
    }

    fun finishedWork() {
        counter = 100
        showProgressNumber(counter)
    }

    fun onDestroy() {
        handler.removeCallbacks(runnable)
    }

    fun getProgressUpdaterService(): LiveData<Int> = progressUpdaterService
}