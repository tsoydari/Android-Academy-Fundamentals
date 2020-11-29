package by.androidacademy.firstapplication.androidservices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ServiceViewModelState {

    private val progressData: MutableLiveData<Int> = MutableLiveData()
    private val isEnableButton: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableIntentService: MutableLiveData<Boolean> = MutableLiveData()
    private val isEnableJobIntentService: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadProgress(): LiveData<Int> = progressData

    fun isEnableDownloadIntentService(): LiveData<Boolean> = isEnableIntentService

    fun isEnableDownloadJobIntentService(): LiveData<Boolean> = isEnableJobIntentService

    fun setProgress(progressData: Int) = this.progressData.postValue(progressData)

    fun isButtonsEnable(): LiveData<Boolean> = isEnableButton

    fun isEnableButton(isEnableButton: Boolean) = this.isEnableButton.postValue(isEnableButton)

    fun isEnableIntentService(isEnableDownloadIntentService: Boolean) =
            this.isEnableIntentService.postValue(isEnableDownloadIntentService)

    fun isEnableJobIntentService(isEnableDownloadJobIntentService: Boolean) =
            this.isEnableJobIntentService.postValue(isEnableDownloadJobIntentService)
}