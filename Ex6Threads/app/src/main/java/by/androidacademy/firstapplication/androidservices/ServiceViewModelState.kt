package by.androidacademy.firstapplication.androidservices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ServiceViewModelState {

    private val progressData: MutableLiveData<Int> = MutableLiveData()
    private val isEnableButton: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadProgress(): LiveData<Int> = progressData

    fun setProgress(progressData: Int) = this.progressData.postValue(progressData)

    fun isButtonsEnable(): LiveData<Boolean> = isEnableButton
}