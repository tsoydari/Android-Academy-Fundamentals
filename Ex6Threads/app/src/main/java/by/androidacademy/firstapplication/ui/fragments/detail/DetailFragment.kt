package by.androidacademy.firstapplication.ui.fragments.detail

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.Movie
import by.androidacademy.firstapplication.dependency.Dependencies
import coil.api.load
import kotlinx.android.synthetic.main.fragment_details.*

private const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
private const val PERMISSIONS_REQUEST_CODE = 1

class DetailFragment : Fragment(R.layout.fragment_details) {

    private val movieDetail: Movie by lazy { DetailFragmentArgs.fromBundle(
        requireArguments()
    ).movieDetail }

    private val detailViewModel: DetailViewModel
            by viewModels {
                DetailViewModelFactory(
                    Dependencies.moviesRepository,
                    movieDetail,
                    requireActivity().application
                )
            }

    private val isPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(requireActivity(), PERMISSION) ==
                PackageManager.PERMISSION_GRANTED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
        initListeners()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the contacts-related task you need to do.
                Log.d("DetailsFragment", "onRequestPermissionsResult # Permission granted")
                startDownloadService()
            } else {
                // permission denied, boo! Disable the functionality that depends on this permission.
                Log.d("DetailsFragment", "onRequestPermissionsResult # Permission denied")
            }
        }
    }

    private fun initUI() {
        movieDetail.apply {
            ivDetailsBack.load(backdropUrl)
            ivDetailsImage.load(posterUrl)
            tvDetailsTitle.text = title
            tvDetailsReleasedDate.text = releaseDate
            tvDetailsOverviewText.text = overview
        }
    }

    private fun initObservers() {
        detailViewModel.trailerUrl.observe(viewLifecycleOwner, Observer { url ->
            openMovieTrailer(url)
        })

        detailViewModel.errorEvent.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListeners() {
        btnDetailsTrailer.setOnClickListener{
            detailViewModel.onTrailerButtonClicked()
        }

        btnDownloadPoster.setOnClickListener {
            downloadPoster()
        }
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun downloadPoster() {
        if (isPermissionGranted) {
            startDownloadService()
        } else {
            requestPermission()
        }
    }

    private fun startDownloadService() {
        Log.d("DetailsFragment", "startDownloadService")
    }

    private fun requestPermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), PERMISSION)) {
            // Show an explanation to the user.
            // After the user sees the explanation, try again to request the permission.
            showExplainingRationaleDialog()
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(PERMISSION),
                    PERMISSIONS_REQUEST_CODE
            )
            // PERMISSIONS_REQUEST_CODE is an app-defined int constant.
            // The callback method gets the result of the request.
        }
    }

    private fun showExplainingRationaleDialog() {
        AlertDialog.Builder(requireActivity())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok) { _, _ ->
                    ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(PERMISSION),
                            PERMISSIONS_REQUEST_CODE
                    )
                }
                .create()
                .show()
    }
}
