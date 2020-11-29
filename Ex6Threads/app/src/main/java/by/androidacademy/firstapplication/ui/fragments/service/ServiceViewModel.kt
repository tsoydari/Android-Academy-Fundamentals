package by.androidacademy.firstapplication.ui.fragments.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.androidservices.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.MAX_PROGRESS
import by.androidacademy.firstapplication.androidservices.ServiceViewModelState
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest

class ServiceViewModel(
    private val heavyWorkManager: HeavyWorkerManager,
    private val viewModelState: ServiceViewModelState,
    private val workerParamsRequest: WorkerParamsRequest
) : ViewModel() {

    private val progressStatus = Observer<Int> { progress ->
        Log.d("progressStatus ", " $progress")
        when (progress) {
            MAX_PROGRESS -> resetState()
            else -> viewModelState.setProgress(progress)
        }
    }

    init {
        subscribeToDownloadProgress()
    }

    override fun onCleared() {
        super.onCleared()
        heavyWorkManager.run {
            resetProgress()
            onDestroy()
        }
        cancelWork()
        unsubscribeDownloadProgress()
    }

    private fun resetState() {
        viewModelState.isEnableButton(true)
        cancelWork()
        heavyWorkManager.run {
            resetProgress()
            onDestroy()
        }
    }

    private fun subscribeToDownloadProgress() {
        heavyWorkManager.getProgressUpdaterService().observeForever(progressStatus)
    }

    private fun unsubscribeDownloadProgress() {
        heavyWorkManager.getProgressUpdaterService().removeObserver(progressStatus)
    }

    fun getWorker() = workerParamsRequest

    private fun cancelWork() = workerParamsRequest.cancelWork()

    fun downloadProgress(): LiveData<Int> = viewModelState.downloadProgress()

    fun isButtonsEnable(): LiveData<Boolean> = viewModelState.isButtonsEnable()
}