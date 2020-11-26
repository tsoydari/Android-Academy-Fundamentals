package by.androidacademy.firstapplication.ui.fragments.service

import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.androidservices.HeavyWorkerManager
import by.androidacademy.firstapplication.androidservices.ServiceViewModelState
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest

class ServiceViewModel(
    private val heavyWorkManager: HeavyWorkerManager,
    private val viewModelState: ServiceViewModelState,
    private val workerParamsRequest: WorkerParamsRequest
) : ViewModel() {

}