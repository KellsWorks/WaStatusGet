package mw.brainytechnologies.wastatusget.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

object FileManager {
    
    fun getStatusDirectory(): File? {
        val statusPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), ".Statuses")
        } else {
            File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/.Statuses")
        }
        return if (statusPath.exists()) statusPath else null
    }

    fun getPhotoStatusFiles(): List<File> {
        val statusDir = getStatusDirectory() ?: return emptyList()
        return statusDir.listFiles { file ->
            file.isFile && (file.extension == "jpg" || file.extension == "jpeg" || file.extension == "png")
        }?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    fun getVideoStatusFiles(): List<File> {
        val statusDir = getStatusDirectory() ?: return emptyList()
        return statusDir.listFiles { file ->
            file.isFile && (file.extension == "mp4" || file.extension == "mkv" || file.extension == "avi")
        }?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    fun getGifStatusFiles(): List<File> {
        val statusDir = getStatusDirectory() ?: return emptyList()
        return statusDir.listFiles { file ->
            file.isFile && file.extension == "gif"
        }?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    fun getAllStatusFiles(): List<File> {
        return getPhotoStatusFiles() + getVideoStatusFiles() + getGifStatusFiles()
    }

    fun copyFileToDownloads(context: Context, sourceFile: File, fileName: String = sourceFile.name): Boolean {
        return try {
            val downloadDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "WaStatusGet")
            } else {
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "WaStatusGet")
            }
            
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }

            val destinationFile = File(downloadDir, fileName)
            sourceFile.copyTo(destinationFile, overwrite = true)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
