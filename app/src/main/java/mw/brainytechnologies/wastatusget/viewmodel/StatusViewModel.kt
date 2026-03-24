package mw.brainytechnologies.wastatusget.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mw.brainytechnologies.wastatusget.utils.FileManager
import java.io.File

data class StatusItem(
    val file: File,
    val name: String,
    val path: String,
    val isVideo: Boolean = false,
    val isGif: Boolean = false
)

class StatusViewModel : ViewModel() {
    
    private val _photoStatuses = MutableStateFlow<List<StatusItem>>(emptyList())
    val photoStatuses: StateFlow<List<StatusItem>> = _photoStatuses

    private val _videoStatuses = MutableStateFlow<List<StatusItem>>(emptyList())
    val videoStatuses: StateFlow<List<StatusItem>> = _videoStatuses

    private val _gifStatuses = MutableStateFlow<List<StatusItem>>(emptyList())
    val gifStatuses: StateFlow<List<StatusItem>> = _gifStatuses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    fun loadStatuses() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            try {
                val photos = FileManager.getPhotoStatusFiles().map { file ->
                    StatusItem(file, file.name, file.absolutePath, isVideo = false, isGif = false)
                }
                val videos = FileManager.getVideoStatusFiles().map { file ->
                    StatusItem(file, file.name, file.absolutePath, isVideo = true, isGif = false)
                }
                val gifs = FileManager.getGifStatusFiles().map { file ->
                    StatusItem(file, file.name, file.absolutePath, isVideo = false, isGif = true)
                }

                _photoStatuses.emit(photos)
                _videoStatuses.emit(videos)
                _gifStatuses.emit(gifs)
            } catch (e: Exception) {
                _snackbarMessage.emit("Error loading statuses: ${e.message}")
            } finally {
                _isLoading.emit(false)
            }
        }
    }

    fun downloadStatus(context: Context, statusItem: StatusItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = FileManager.copyFileToDownloads(context, statusItem.file)
                if (success) {
                    _snackbarMessage.emit("Status saved successfully!")
                } else {
                    _snackbarMessage.emit("Failed to save status")
                }
            } catch (e: Exception) {
                _snackbarMessage.emit("Error: ${e.message}")
            }
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}
