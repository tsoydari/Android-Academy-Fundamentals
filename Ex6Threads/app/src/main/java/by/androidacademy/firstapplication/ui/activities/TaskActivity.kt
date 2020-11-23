package by.androidacademy.firstapplication.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.threads.CounterCoroutinesTask
import by.androidacademy.firstapplication.threads.TaskEventContract
import by.androidacademy.firstapplication.ui.fragments.CounterFragment
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

class TaskActivity : AppCompatActivity(),
    TaskEventContract.Lifecycle,
    TaskEventContract.Operationable {

    private val taskFragment: CounterFragment by lazy { CounterFragment.newInstance("Task activity") }
    private var task: CounterCoroutinesTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, taskFragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        task?.coroutineContext?.cancelChildren()
        task?.coroutineContext?.cancel()
    }

    override fun createTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show()
        if (task == null) {
            task = CounterCoroutinesTask(this)
        }
        task?.createTask()
    }

    override fun startTask() {
        val started = task?.start()

        if (started == null || started == false) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        }
    }

    override fun cancelTask() {
        val canceled = task?.cancel()

        if (canceled == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
        }
        task = null
    }

    override fun onPreExecute() {
        taskFragment?.updateFragmentText(getString(R.string.task_created))
    }

    override fun onPostExecute() {
        taskFragment?.updateFragmentText(getString(R.string.done))
    }

    override fun onProgressUpdate(progress: Int) {
        taskFragment?.updateFragmentText(progress.toString())
    }

    override fun onCancel() {
        Toast.makeText(this, getString(R.string.msg_oncancel), Toast.LENGTH_SHORT).show()
    }
}