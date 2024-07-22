package org.akai.sciclubhub.ui.components

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient

@Composable
fun MainNavScreen(
    navController: NavHostController,
    client: HttpClient,
    preferences: SharedPreferences
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
    ) {
        NavHost(navController = navController, startDestination = "home_view") {
            composable("home_view") { }
            composable("user_view") { }
            composable("message_view") { }
            composable("calendar_view") { }
        }
        //top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Yellow)
                .align(alignment = Alignment.TopStart)
        ) {}

            //bottom bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Cyan)
                .align(alignment = Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
fun MainNavScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferences by remember { mutableStateOf(context.getSharedPreferences("sign_in", Context.MODE_PRIVATE)) }

    MainNavScreen(
        navController = navController,
        client = HttpClient(),
        preferences = preferences
    )

}