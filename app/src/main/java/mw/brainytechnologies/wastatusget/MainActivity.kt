package mw.brainytechnologies.wastatusgetpackage mw.brainytechnologies.wastatusget



import android.os.Bundleimport android.os.Bundle

import androidx.activity.ComponentActivityimport androidx.activity.ComponentActivity

import androidx.activity.compose.setContentimport androidx.activity.compose.setContent

import androidx.activity.enableEdgeToEdgeimport androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.backgroundimport androidx.compose.foundation.background

import androidx.compose.foundation.layout.Boximport androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxSizeimport androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.paddingimport androidx.compose.material3.Scaffold

import androidx.compose.material3.Scaffoldimport androidx.compose.material3.Text

import androidx.compose.runtime.Composableimport androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffectimport androidx.compose.runtime.getValue

import androidx.compose.runtime.collectAsStateimport androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.getValueimport androidx.compose.runtime.remember

import androidx.compose.runtime.mutableIntStateOfimport androidx.compose.runtime.setValue

import androidx.compose.runtime.rememberimport androidx.compose.ui.Modifier

import androidx.compose.runtime.setValueimport androidx.compose.ui.graphics.Color

import androidx.compose.ui.Modifierimport androidx.compose.ui.unit.dp

import androidx.compose.ui.graphics.Colorimport mw.brainytechnologies.wastatusget.ui.components.AppHeader

import androidx.compose.ui.unit.dpimport mw.brainytechnologies.wastatusget.ui.components.Tabs

import androidx.lifecycle.viewmodel.compose.viewModelimport mw.brainytechnologies.wastatusget.ui.theme.WaStatusGetTheme

import mw.brainytechnologies.wastatusget.ui.components.AppHeader

import mw.brainytechnologies.wastatusget.ui.components.SnackbarNotificationclass MainActivity : ComponentActivity() {

import mw.brainytechnologies.wastatusget.ui.components.StatusGrid    override fun onCreate(savedInstanceState: Bundle?) {

import mw.brainytechnologies.wastatusget.ui.components.Tabs        super.onCreate(savedInstanceState)

import mw.brainytechnologies.wastatusget.ui.theme.WaStatusGetTheme        enableEdgeToEdge()

import mw.brainytechnologies.wastatusget.viewmodel.StatusViewModel        setContent {

            WaStatusGetTheme {

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {                val tabs = listOf("Photos", "Videos", "GIFs")

        super.onCreate(savedInstanceState)                var selectedTab by remember { mutableIntStateOf(1) }

        enableEdgeToEdge()

        setContent {                Scaffold(

            WaStatusGetTheme {                    modifier = Modifier.fillMaxSize().

                MainScreen(this)                    background(Color(0xFFECFDF5)),

            }                    topBar = {

        }                        AppHeader(

    }                            title = "WaStatusGet",

}                            onMenuClick = {},

                        )

@Composable                    }

fun MainScreen(activity: MainActivity) {

    val viewModel: StatusViewModel = viewModel()                ) { padding ->

    val tabs = listOf("Photos", "Videos", "GIFs")

    var selectedTab by remember { mutableIntStateOf(0) }                    Column(

                        modifier = Modifier

    val photoStatuses by viewModel.photoStatuses.collectAsState()                            .padding(padding)

    val videoStatuses by viewModel.videoStatuses.collectAsState()                            .fillMaxSize()

    val gifStatuses by viewModel.gifStatuses.collectAsState()                    ) {

    val isLoading by viewModel.isLoading.collectAsState()                        Tabs(

    val snackbarMessage by viewModel.snackbarMessage.collectAsState()                            tabs = tabs,

                            selectedTab = selectedTab,

    // Load statuses on first composition                            onTabSelected = { selectedTab = it },

    LaunchedEffect(Unit) {                            modifier = Modifier

        viewModel.loadStatuses()                                .fillMaxWidth()

    }                                .padding(8.dp)

                        )

    Scaffold(

        modifier = Modifier                        when (selectedTab) {

            .fillMaxSize()                            0 -> Text("Photos")

            .background(Color(0xFFECFDF5)),                            1 -> Text("Videos")

        topBar = {                            2 -> Text("GIFs")

            AppHeader(                        }

                title = "WaStatusGet",                    }

                onMenuClick = {},                }

            )            }

        }        }

    ) { padding ->}

        Box(fun onTabSelected(){}

            modifier = Modifier@Composable

                .padding(padding)fun Greeting(name: String, modifier: Modifier = Modifier) {

                .fillMaxSize()    Text(

                .background(Color(0xFFECFDF5))        text = "Hello $name!",

        ) {        modifier = modifier

            Column(    )

                modifier = Modifier}

                    .fillMaxSize()    }
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
