package org.akai.sciclubhub.ui.components

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient

@Composable
fun LoginScreen(modifier: Modifier = Modifier, preferences: SharedPreferences?, client: HttpClient? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        BasicLogin(
            modifier = modifier,
            preferences = preferences,
            client = client
        )

        Spacer(modifier = modifier.fillMaxHeight(1/3f))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = Icons.Default.Adb,
                contentDescription = "placeholder",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = modifier.fillMaxWidth(0.2f))
            Icon(
                imageVector = Icons.Default.Apps,
                contentDescription = "placeholder",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = modifier.fillMaxWidth(0.2f))
            Icon(
                imageVector = Icons.Default.Adjust,
                contentDescription = "placeholder",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(preferences = null)
}