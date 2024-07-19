package org.akai.sciclubhub

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.akai.sciclubhub.ktor.KtorClient
import org.akai.sciclubhub.ui.components.LoginScreen
import org.akai.sciclubhub.ui.theme.SciClubHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SciClubHubTheme {
                val assets = LocalContext.current.assets

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        preferences = this.getPreferences(Context.MODE_PRIVATE),
                        modifier = Modifier.padding(innerPadding),
                        client = KtorClient.getClient(assets)
                    )
                }
            }
        }


    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SciClubHubTheme {
        Greeting("Android")
    }
}