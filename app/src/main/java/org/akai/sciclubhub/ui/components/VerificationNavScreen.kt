package org.akai.sciclubhub.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.akai.sciclubhub.ktor.KtorClient

@Composable
fun VerificationNavScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val apiClient by remember { mutableStateOf(KtorClient.getClient(context.assets)) }
    val preferences by remember { mutableStateOf(context.getSharedPreferences("sign_in", Context.MODE_PRIVATE)) }

    NavHost(navController = navController, startDestination = "sign_in") {
        composable("sign_in") {
            LoginScreen(
                preferences = preferences,
                client = apiClient,
                afterLoginSuccess = { navController.navigate("open") { launchSingleTop = true } }
            )
        }
        composable("sign_up") {
            TODO("Sign up screen")
        }
        composable("password_recovery") {
            TODO("Password recovery screen")
        }
        composable("open") {
            MainNavScreen(
                navController = navController,
                client = apiClient,
                preferences = preferences
            )
        }
    }
}

@Preview
@Composable
fun NavScreenPreview() {
    VerificationNavScreen(
        navController = rememberNavController()
    )
}
