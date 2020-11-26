package by.androidacademy.firstapplication.ui.fragments.service

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.dependency.Dependencies

class ServiceFragment : Fragment(R.layout.fragment_bg_service) {

    companion object {
        const val FRAGMENT_TAG = "fragment_counterFragment"
    }

    private val serviceViewModel: ServiceViewModel by viewModels {
        ServiceViewModelFactory( Dependencies.heavyWorkManager,
        Dependencies.serviceViewModelState(),
        Dependencies.workerParamsRequest)}

}