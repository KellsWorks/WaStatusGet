package mw.brainytechnologies.wastatusget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mw.brainytechnologies.wastatusget.ui.components.AppHeader
import mw.brainytechnologies.wastatusget.ui.components.Tabs
import mw.brainytechnologies.wastatusget.ui.theme.WaStatusGetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaStatusGetTheme {

                val tabs = listOf("Photos", "Videos", "GIFs")
                var selectedTab by remember { mutableIntStateOf(1) }

                Scaffold(
                    modifier = Modifier.fillMaxSize().
                    background(Color(0xFFECFDF5)),
                    topBar = {
                        AppHeader(
                            title = "WaStatusGet",
                            onMenuClick = {},
                        )
                    }

                ) { padding ->

                    Column(
                        modifier = Modifier
                            .padding(padding)
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

                        when (selectedTab) {
                            0 -> Text("Photos Content")
                            1 -> Text("Videos Content")
                            2 -> Text("GIFs Content")
                        }
                    }
                }
            }
        }
}
fun onTabSelected(){}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
    }