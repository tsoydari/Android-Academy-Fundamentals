package by.androidacademy.firstapplication.ui.fragments.service

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.androidservices.ServiceDelegate
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
        Dependencies.workerParamsRequest) }

    private val serviceDelegate: ServiceDelegate = Dependencies.serviceDelegate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceDelegate.stopAllService(requireContext())
    }

    private fun initListeners() {

        btnWorker.setOnClickListener {
            setSateForBtn(false)
            serviceViewModel.getWorker().run {
                enqueue()
                subscribeToUpdateWorker(this)
            }
        }

        btnIntentService.setOnClickListener {
            setSateForBtn(false)
            serviceDelegate.startDownloadIntentService(requireContext(), true)
        }

        btnService.setOnClickListener {
            setSateForBtn(false)
            serviceDelegate.startDownloadService(requireContext(), true)
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

            isEnableDownloadIntentService().observe(viewLifecycleOwner, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadIntentService()
                }
            })
            isEnableDownloadJobIntentService().observe(viewLifecycleOwner, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadJobIntentService()
                }
            })
            isEnableDownloadService().observe(viewLifecycleOwner, Observer { isEnable ->
                if (!isEnable) {
                    stopDownloadService()
                }
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
        btnIntentService.isEnabled = isEnable
        btnService.isEnabled = isEnable
    }

    private fun subscribeToUpdateWorker(params: WorkerParamsRequest) {
        params.workManagerInfo()?.observe(viewLifecycleOwner, Observer { workInfo ->
                    Toast.makeText(requireContext(), workInfo.state.name, Toast.LENGTH_SHORT).show()
                })
    }

    private fun stopDownloadIntentService() {
        serviceDelegate.stopDownloadIntentService(requireContext())
    }

    private fun stopDownloadJobIntentService() {
        serviceDelegate.stopDownloadJobIntentService(requireContext())
    }

    private fun stopDownloadService() {
        serviceDelegate.stopDownloadService(requireContext())
    }
}