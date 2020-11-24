package by.androidacademy.firstapplication.threads

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.androidacademy.firstapplication.R
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

class CoroutinesViewModel(val stringsProvider: StringsProvider): ViewModel() {

    val textMutableLiveData = MutableLiveData<String>()
    private var coroutineTask: CounterCoroutinesTask? = null
    private val listener: TaskEventContract.Lifecycle = createListeners()

    override fun onCleared() {
        super.onCleared()
        coroutineTask?.coroutineContext?.cancelChildren()
        coroutineTask?.coroutineContext?.cancel()
        coroutineTask?.cancel()
        coroutineTask = null
    }

    private fun createListeners() = object  : TaskEventContract.Lifecycle {
        override fun onProgressUpdate(progress: Int) {
            textMutableLiveData.value = progress.toString()
        }

        override fun onPreExecute() = Unit

        override fun onPostExecute() {
            textMutableLiveData.value = stringsProvider.getString(R.string.done)
        }

        override fun onCancel() = Unit
    }

    fun onCreateTask() {
        textMutableLiveData.value = stringsProvider.getString(R.string.msg_oncreate)

        coroutineTask = CounterCoroutinesTask(listener)
                .apply { createTask() }
    }

    fun onStartTask() {
        val started = coroutineTask?.start()

        if (started == null || started == false) {
            textMutableLiveData.value = stringsProvider.getString(R.string.msg_should_create_task)
        }
    }

    fun onCancelTask() {
//        val canceled = coroutineTask?.cancel()
//        val canceled = onCleared()

        if (coroutineTask == null) {
            textMutableLiveData.value = stringsProvider.getString(R.string.msg_should_create_task)
        }
        coroutineTask?.cancel()
    }
}