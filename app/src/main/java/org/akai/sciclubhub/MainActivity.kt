package org.akai.sciclubhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import org.akai.sciclubhub.ui.components.VerificationNavScreen
import org.akai.sciclubhub.ui.theme.SciClubHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SciClubHubTheme {
                val assets = LocalContext.current.assets

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    VerificationNavScreen(
                        navController = rememberNavController(),
                        padding = innerPadding,
//                        preferences = this.getPreferences(Context.MODE_PRIVATE),
//                        modifier = Modifier.padding(innerPadding),
//                        client = KtorClient.getClient(assets)
                    )
                }
            }
        }
    }
}