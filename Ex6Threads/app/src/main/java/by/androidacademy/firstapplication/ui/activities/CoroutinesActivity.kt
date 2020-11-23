package by.androidacademy.firstapplication.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.threads.CoroutinesViewModel
import by.androidacademy.firstapplication.threads.CoroutinesViewModelFactory
import by.androidacademy.firstapplication.threads.CounterCoroutinesTask
import by.androidacademy.firstapplication.threads.TaskEventContract
import by.androidacademy.firstapplication.ui.fragments.CounterFragment
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

class CoroutinesActivity : AppCompatActivity(),
    TaskEventContract.Operationable {

    private val taskFragment: CounterFragment by lazy { CounterFragment.newInstance("Task activity") }
    private val coroutinesViewModel: CoroutinesViewModel by viewModels { CoroutinesViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, taskFragment)
                .commit()
        }

        initObservers()
    }

    override fun createTask() {
        coroutinesViewModel.onCreateTask()
    }

    override fun startTask() {
        coroutinesViewModel.onStartTask()
    }

    override fun cancelTask() {
        coroutinesViewModel.onCancelTask()
    }

    private fun initObservers() {
        coroutinesViewModel.textMutableLiveData.observe(this, Observer { text ->
            taskFragment?.updateFragmentText(text)
        })
    }
}