package org.akai.sciclubhub.ui.components

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import org.akai.sciclubhub.R
import org.akai.sciclubhub.ktor.KtorClient
import org.akai.sciclubhub.ui.components.navigationbottombar.NavigationBottomBar

@Composable
fun MainNavScreen(
    navController: NavHostController,
    client: HttpClient,
    preferences: SharedPreferences
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        bottomBar = { NavigationBottomBar(navController = navController)},
        topBar = {}
    ) {
        NavHost(
            navController = navController,
            startDestination = stringResource(R.string.home_view_destination)
        ) {
            composable(context.getString(R.string.home_view_destination)) {
                PlaceholderView(title = "Home view")
            }
            composable(context.getString(R.string.user_account_view_destination)) {
                PlaceholderView(title = "User account view")
            }
            composable(context.getString(R.string.chat_view_destination)) {
                PlaceholderView(title = "Chat view")
            }
            composable(context.getString(R.string.calendar_view_destination)) {
                PlaceholderView(title = "Calendar view")
            }
        }
    }
}

@Preview
@Composable
fun MainNavScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferences =
            context.getSharedPreferences(
                context.getString(R.string.user_sign_in_preferences),
                Context.MODE_PRIVATE
            )



    MainNavScreen(
        navController = navController,
        client = KtorClient.getClient(context.assets),
        preferences = preferences
    )

}