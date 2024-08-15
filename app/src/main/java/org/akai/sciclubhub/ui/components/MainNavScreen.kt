package org.akai.sciclubhub.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.akai.sciclubhub.R
import org.akai.sciclubhub.clubhubclient.Authorized
import org.akai.sciclubhub.clubhubclient.queries.getUserInfo
import org.akai.sciclubhub.clubhubclient.queries.getUsersClubs
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.club.Club
import org.akai.sciclubhub.data.user.User
import org.akai.sciclubhub.ui.components.navigationbottombar.NavigationBottomBar

@Composable
fun MainNavScreen(
    authorization: Authorized
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val coroutine = rememberCoroutineScope()
    var userInfo by remember { mutableStateOf<User?>(null) }
    val userClubs = remember { mutableStateListOf<Club>() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        bottomBar = {
            NavigationBottomBar(
                navController = navController,
                onClickActions = listOf {
                    coroutine.launch {
                        try {
                            userInfo = getUserInfo(authorization, authorization.uuid)
                            try {
                                userClubs.addAll(getUsersClubs(authorization, authorization.uuid))
                            } catch (e: Exception) {
                                Log.e("Loading Clubs", e.message?: "Unknown error")
                            }
                        } catch (e: Exception) {
                            Log.e("Loading Account", e.message?: "Unknown error")
                        }
                    }
                }
            )
        },
        topBar = {}
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = stringResource(R.string.home_view_destination)
        ) {
            composable(context.getString(R.string.home_view_destination)) {
                PlaceHolderView(title = "Home view")
            }
            composable(context.getString(R.string.user_account_view_destination)) {
                UserAccountView(
                    user = userInfo,
                    usersClubs = userClubs,
                    usersProjects = emptyList(),
                    padding = innerPadding
                )
            }
            composable(context.getString(R.string.chat_view_destination)) {
                PlaceHolderView(title = "Chat view")
            }
            composable(context.getString(R.string.calendar_view_destination)) {
                PlaceHolderView(title = "Calendar view")
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_DESK,
    backgroundColor = 0xFFFF5722
)
@Composable
fun MainNavScreenPreview() {
    MainNavScreen(
        authorization = Authorized(UUID.randomUUID(), "23312")
    )

}