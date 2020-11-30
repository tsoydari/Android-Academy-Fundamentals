package by.androidacademy.firstapplication.androidservices

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build


class ServiceDelegate {

    fun startDownloadIntentService(context: Context, isEnable: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val service = Intent(context, DownloadJobIntentService::class.java)
            service.putExtra(SERVICE_INT_DATA, isEnable)
            DownloadJobIntentService.enqueueWork(context, service)
        } else {
            val service = Intent(context, DownloadIntentService::class.java)
            service.putExtra(SERVICE_INT_DATA, isEnable)
            context.startService(service)
        }
    }

    fun startDownloadService(context: Context, isEnable: Boolean) {
        val service = Intent(context, DownloadService::class.java)
        service.putExtra(SERVICE_INT_DATA, isEnable)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service)
        } else {
            context.startService(service)
        }
    }


    fun stopDownloadIntentService(context: Context) {
        val service = Intent(context, DownloadIntentService::class.java)
        context.stopService(service)
    }

    fun stopDownloadJobIntentService(context: Context) {
        val service = Intent(context, DownloadJobIntentService::class.java)
        context.stopService(service)
    }

    fun stopDownloadService(context: Context) {
        val service = Intent(context, DownloadService::class.java)
        context.stopService(service)
    }

    fun stopAllService(context: Context) {
        stopDownloadIntentService(context)
        stopDownloadJobIntentService(context)
        stopDownloadService(context)
    }
}