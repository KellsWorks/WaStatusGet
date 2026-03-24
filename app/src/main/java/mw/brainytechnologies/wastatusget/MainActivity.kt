package mw.brainytechnologies.wastatusget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mw.brainytechnologies.wastatusget.ui.components.AppHeader
import mw.brainytechnologies.wastatusget.ui.components.SnackbarNotification
import mw.brainytechnologies.wastatusget.ui.components.StatusGrid
import mw.brainytechnologies.wastatusget.ui.components.Tabs
import mw.brainytechnologies.wastatusget.ui.theme.WaStatusGetTheme
import mw.brainytechnologies.wastatusget.viewmodel.StatusViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaStatusGetTheme {
                MainScreen(this)
            }
        }
    }
}

@Composable
fun MainScreen(activity: MainActivity) {
    val viewModel: StatusViewModel = viewModel()
    val tabs = listOf("Photos", "Videos", "GIFs")
    var selectedTab by remember { mutableIntStateOf(0) }

    val photoStatuses by viewModel.photoStatuses.collectAsState()
    val videoStatuses by viewModel.videoStatuses.collectAsState()
    val gifStatuses by viewModel.gifStatuses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()

    // Load statuses on first composition
    LaunchedEffect(Unit) {
        viewModel.loadStatuses()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECFDF5)),
        topBar = {
            AppHeader(
                title = "WaStatusGet",
                onMenuClick = {},
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFECFDF5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Tabs(
                    tabs = tabs,
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    when (selectedTab) {
                        0 -> StatusGrid(
                            statuses = photoStatuses,
                            isLoading = isLoading,
                            onDownload = { status ->
                                viewModel.downloadStatus(activity, status)
                            }
                        )
                        1 -> StatusGrid(
                            statuses = videoStatuses,
                            isLoading = isLoading,
                            onDownload = { status ->
                                viewModel.downloadStatus(activity, status)
                            }
                        )
                        2 -> StatusGrid(
                            statuses = gifStatuses,
                            isLoading = isLoading,
                            onDownload = { status ->
                                viewModel.downloadStatus(activity, status)
                            }
                        )
                    }
                }
            }

            // Snackbar notification
            snackbarMessage?.let { message ->
                SnackbarNotification(
                    message = message,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(androidx.compose.ui.Alignment.BottomCenter)
                )
                LaunchedEffect(message) {
                    kotlinx.coroutines.delay(3000)
                    viewModel.clearSnackbarMessage()
                }
            }
        }
    }
}
