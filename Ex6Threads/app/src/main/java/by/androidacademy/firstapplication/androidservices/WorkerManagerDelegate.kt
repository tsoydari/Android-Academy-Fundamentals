package by.androidacademy.firstapplication.androidservices

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import by.androidacademy.firstapplication.dependency.Dependencies


class WorkerManagerDelegate(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val heavyWorkManager: HeavyWorkerManager = Dependencies.heavyWorkManager

    override fun doWork(): Result {
        return try {
            heavyWorkManager.startWork()
            Thread.sleep(DELAY_VALUE)
            Result.success()
        } catch (error: Throwable) {
            heavyWorkManager.resetProgress()
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
