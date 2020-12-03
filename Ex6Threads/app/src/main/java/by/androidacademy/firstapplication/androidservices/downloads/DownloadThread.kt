package by.androidacademy.firstapplication.androidservices.downloads

import android.util.Log
import by.androidacademy.firstapplication.dependency.Dependencies
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


private const val UPDATE_TIMEOUT = 1000

class DownloadThread(
        private val url: String,
        private val downloadCallBack: DownloadCallBack
) : Thread() {

    private var progress = 0
    private var lastUpdateTime: Long = 0

    override fun run() {
        Log.d("DownloadThread", "#run")
        val file = createFile()
        if (file == null) {
            downloadCallBack.onError("Can't create file")
            return
        }
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            val url = URL(url)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                downloadCallBack.onError(
                        "Server returned HTTP response code: "
                                + connection.responseCode + " - " + connection.responseMessage
                )
                return
            }
            val fileLength = connection.contentLength
            Log.d("DownloadThread", "File size: " + fileLength / 1024 + " KB")
            // Input stream (Downloading file)
            inputStream = BufferedInputStream(url.openStream(), 8192)
            // Output stream (Saving file)
            fos = FileOutputStream(file.path)
            var next: Int
            val data = ByteArray(1024)
            while (inputStream.read(data).also { next = it } != -1) {
                fos.write(data, 0, next)
                updateProgress(fos, fileLength)
            }
            downloadCallBack.onDownloadFinished(file.path)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            downloadCallBack.onError("MalformedURLException: " + e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            downloadCallBack.onError("IOException: " + e.message)
        } finally {
            try {
                if (fos != null) {
                    fos.flush()
                    fos.close()
                }
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            connection?.disconnect()
        }
    }

    @Throws(IOException::class)
    private fun updateProgress(fos: FileOutputStream, fileLength: Int) {
        if (System.currentTimeMillis() > lastUpdateTime + UPDATE_TIMEOUT) {
            val count = fos.channel.size().toInt() * 100 / fileLength
            if (count > progress) {
                progress = count
                lastUpdateTime = System.currentTimeMillis()
                downloadCallBack.onProgressUpdate(progress)
            }
        }
    }

    private fun createFile(): File? {
        // Create the storage directory if it does not exist
        val dirOutput = Dependencies.downloadsFile
        dirOutput?.run {
            if (this.exists() ) {
                if (this.mkdirs()) {
                    return null
                }
            }
            // Create a media file name
            return File(this.path, createFileName())
        }
        return null
    }

    private fun createFileName(): String {
        return "${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}_${File(URI(url).path).name}"
    }

    interface DownloadCallBack {
        fun onProgressUpdate(percent: Int)
        fun onDownloadFinished(filePath: String)
        fun onError(error: String)
    }
}