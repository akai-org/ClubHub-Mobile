package org.akai.sciclubhub.ui.components

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.akai.sciclubhub.R
import org.akai.sciclubhub.clubhubclient.Authorized

@Composable
fun VerificationNavScreen(
    navController: NavHostController,
    padding: PaddingValues?
) {
    val context = LocalContext.current

    val preferences =
            context.getSharedPreferences(
                context.getString(R.string.user_sign_in_preferences),
                Context.MODE_PRIVATE
            )

    var authorization by remember { mutableStateOf<Authorized?>(null) }


    NavHost(navController = navController, startDestination = stringResource(R.string.sign_in_view_destination)) {
        composable(context.getString(R.string.sign_in_view_destination)) {
            LoginScreen(
                preferences = preferences,
                afterLoginSuccess = { 
                    navController.navigate(context.getString(R.string.confirmed_destination)) {
                        launchSingleTop = true
                    }
                    authorization = it
                }
            )
        }
        composable(context.getString(R.string.sign_up_view_destination)) {
            TODO("Sign up screen")
        }
        composable(context.getString(R.string.password_recovery_view_destination)) {
            TODO("Password recovery screen")
        }
        composable(context.getString(R.string.confirmed_destination)) {
            MainNavScreen(
                authorization = authorization!!
            )
        }
    }
}

@Preview
@Composable
fun VerificationNavScreenPreview() {
    val navController = rememberNavController()
    VerificationNavScreen(
        navController = navController ,
        padding = null
    )
}
