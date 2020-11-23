package by.androidacademy.firstapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.threads.TaskEventContract
import kotlinx.android.synthetic.main.fragment_threads.btnCancel
import kotlinx.android.synthetic.main.fragment_threads.btnCreate
import kotlinx.android.synthetic.main.fragment_threads.btnStart
import kotlinx.android.synthetic.main.fragment_threads.tvValue

class CounterFragment : Fragment(R.layout.fragment_threads) {

    companion object {
        fun newInstance(fragmentTitle: String): CounterFragment {
            return CounterFragment()
        }
    }

    private var listener: TaskEventContract.Operationable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity != null && activity is TaskEventContract.Operationable) {
            listener = activity as TaskEventContract.Operationable
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnCreate.setOnClickListener { listener?.createTask() }
        btnStart.setOnClickListener { listener?.startTask() }
        btnCancel.setOnClickListener { listener?.cancelTask() }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    fun updateFragmentText(text: String) {
        tvValue.text = text
    }
}
