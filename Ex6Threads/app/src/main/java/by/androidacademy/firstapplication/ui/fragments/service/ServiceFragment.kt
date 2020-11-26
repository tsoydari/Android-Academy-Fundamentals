package by.androidacademy.firstapplication.ui.fragments.service

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.androidservices.WorkerParamsRequest
import by.androidacademy.firstapplication.dependency.Dependencies
import kotlinx.android.synthetic.main.fragment_bg_service.*

class ServiceFragment : Fragment(R.layout.fragment_bg_service) {

    companion object {
        const val FRAGMENT_TAG = "fragment_counterFragment"
    }

    private val serviceViewModel: ServiceViewModel by viewModels {
        ServiceViewModelFactory( Dependencies.heavyWorkManager,
        Dependencies.serviceViewModelState(),
        Dependencies.workerParamsRequest)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {

        btnWorker.setOnClickListener {
            setSateForBtn(false)
            serviceViewModel?.run {
                getWorker().run {
                    enqueue()
                    subscribeToUpdateWorker(this)
                }
            }
        }
    }

    private fun initObservers() {
        serviceViewModel.run{
            downloadProgress().observe(viewLifecycleOwner, Observer {
                setProgress(it.toString())
            })

            isButtonsEnable().observe(viewLifecycleOwner, Observer {
                setSateForBtn(it)
            })
        }
    }

    private fun setProgress(progress: String) {
        activity?.runOnUiThread {
            tvResultProgress.text = progress
        }
    }

    private fun setSateForBtn(isEnable: Boolean) {
        btnWorker.isEnabled = isEnable
    }

    private fun subscribeToUpdateWorker(params: WorkerParamsRequest) {
        activity?.run {
            params.workManagerInfo()?.observe(viewLifecycleOwner, Observer { workInfo ->
                    Toast.makeText(this, workInfo.state.name, Toast.LENGTH_SHORT).show()
                })
        }
    }

}