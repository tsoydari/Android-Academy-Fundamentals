package by.androidacademy.firstapplication.threads

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val LOG_TAG = "NewCoroutineTask"
private const val MAX_COUNTER_VALUE = 5
private const val DELAY_IN_MILLS = 500L


class CounterCoroutinesTask(private val listener: TaskEventContract.Lifecycle): CoroutineScope {

    private val job = SupervisorJob()
    private var newJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    fun createTask() {

        newJob = launch(start = CoroutineStart.LAZY) {
            Log.d(LOG_TAG, "Start job on IO thread | thread: ${Thread.currentThread().name}")

            repeat(MAX_COUNTER_VALUE) { counter ->
                Log.d(
                    LOG_TAG,
                    "New counter value [counter: $counter] | thread: ${Thread.currentThread().name}"
                )

                withContext(Dispatchers.Main) {

                }

                launch(Dispatchers.Main) {
                    Log.d(
                        LOG_TAG,
                        "Switch thread to main [counter: $counter] | thread: ${Thread.currentThread().name}"
                    )

                    listener.onProgressUpdate(counter)
                }

                delay(DELAY_IN_MILLS)
            }

            launch(Dispatchers.Main) {
                Log.d(
                    LOG_TAG,
                    "Switch thread to main again | thread: ${Thread.currentThread().name}"
                )

                listener.onPostExecute()
            }
        }
        launch(Dispatchers.Main) {
            Log.d(
                LOG_TAG,
                "Setting up the task | thread: ${Thread.currentThread().name}"
            )
            listener.onPreExecute()
        }
    }


    fun cancel() {
        Log.d(LOG_TAG, "Before 'cancel' of job | thread: ${Thread.currentThread().name}")

        newJob?.cancel()
        coroutineContext.cancel()

        Log.d(LOG_TAG, "After 'cancel' of job | thread: ${Thread.currentThread().name}")
    }

    fun start(): Boolean? {
        Log.d(LOG_TAG, "Before 'start' of job | thread: ${Thread.currentThread().name}")
        val started = newJob?.start()
        Log.d(
            LOG_TAG,
            "After 'start' of job (started: $started) | thread: ${Thread.currentThread().name}"
        )

        return started
    }

}