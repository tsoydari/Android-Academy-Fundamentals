package by.androidacademy.firstapplication.ui.fragments.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.androidacademy.firstapplication.androidservices.threads.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.threads.ServiceViewModelState
import by.androidacademy.firstapplication.androidservices.threads.WorkerParamsRequest


class ServiceViewModelFactory(
        private val heavyWorkManager: HeavyWorkerManager,
        private val serviceViewModelState: ServiceViewModelState,
        private val workerParamsRequest: WorkerParamsRequest
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == ServiceViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            ServiceViewModel(
                heavyWorkManager,
                serviceViewModelState,
                workerParamsRequest
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}