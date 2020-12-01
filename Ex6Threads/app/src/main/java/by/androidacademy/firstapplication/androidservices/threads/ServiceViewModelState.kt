package by.androidacademy.firstapplication.androidservices.threads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ServiceViewModelState {

    private val progressData: MutableLiveData<Int> = MutableLiveData(0)
    private val isEnableButton: MutableLiveData<Boolean> = MutableLiveData(true)
    private val isEnableService: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isEnableIntentService: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isEnableJobIntentService: MutableLiveData<Boolean> = MutableLiveData(false)

    fun downloadProgress(): LiveData<Int> = progressData

    fun isEnableDownloadService(): LiveData<Boolean> = isEnableService

    fun isEnableDownloadIntentService(): LiveData<Boolean> = isEnableIntentService

    fun isEnableDownloadJobIntentService(): LiveData<Boolean> = isEnableJobIntentService

    fun setProgress(progressData_: Int) = progressData.postValue(progressData_)

    fun isButtonsEnable(): LiveData<Boolean> = isEnableButton

    fun isEnableButton(isEnableButton_: Boolean) = isEnableButton.postValue(isEnableButton_)

    fun isEnableService(isEnableDownloadService: Boolean) =
            this.isEnableService.postValue(isEnableDownloadService)

    fun isEnableIntentService(isEnableDownloadIntentService: Boolean) =
            this.isEnableIntentService.postValue(isEnableDownloadIntentService)

    fun isEnableJobIntentService(isEnableDownloadJobIntentService: Boolean) =
            this.isEnableJobIntentService.postValue(isEnableDownloadJobIntentService)
}