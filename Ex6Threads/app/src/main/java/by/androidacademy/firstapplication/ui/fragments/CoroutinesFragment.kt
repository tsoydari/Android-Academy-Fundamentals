package by.androidacademy.firstapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.threads.CoroutinesViewModel
import by.androidacademy.firstapplication.threads.CoroutinesViewModelFactory
import by.androidacademy.firstapplication.threads.TaskEventContract
import kotlinx.android.synthetic.main.fragment_threads.*

class CoroutinesFragment: Fragment(R.layout.fragment_threads),
        TaskEventContract.Operationable {

    private val coroutinesViewModel: CoroutinesViewModel by viewModels { CoroutinesViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
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
        coroutinesViewModel.textMutableLiveData.observe(viewLifecycleOwner, Observer { text ->
            tvValue.text = text
        })
    }

    private fun initListeners() {
        btnCreate.setOnClickListener { createTask() }
        btnStart.setOnClickListener { startTask() }
        btnCancel.setOnClickListener { cancelTask() }
    }

}