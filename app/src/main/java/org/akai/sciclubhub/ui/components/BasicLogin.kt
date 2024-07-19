package org.akai.sciclubhub.ui.components

import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import org.akai.sciclubhub.R
import org.akai.sciclubhub.ktor.KtorClient.login

@Composable
fun BasicLogin(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    preferences: SharedPreferences? = null,
    client: HttpClient? = null,
    afterLoginSuccess: () -> Unit = {},
    forgotPasswordAction: () -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        OutlinedEmailField(
            value = email,
            onValueChange = onEmailChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedPasswordField(
            value = password,
            onValueChange = onPasswordChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.clickable { forgotPasswordAction() },
            text = "Forgot password?"
        )

        Spacer(modifier = Modifier.height(24.dp))

        var rememberMeChecked by remember {
            mutableStateOf(preferences?.getBoolean("remember_me", false) ?: false)
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            Text(text = stringResource(R.string.remember_me_text))
            Checkbox(
                checked = rememberMeChecked,
                onCheckedChange = { rememberMeChecked = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    client?.login(email, password).let {
                        preferences?.edit()?.putBoolean("remember_me", rememberMeChecked)?.apply()
                        preferences?.edit()?.putString("user_token", it)?.apply()
                        afterLoginSuccess()
                    }
                }
            },
            enabled = email.isNotBlank() && password.isNotBlank(),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 5.dp,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier.size(25.dp)
                    )
                }
                else -> {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Login",
                        tint = if (email.isNotBlank() && password.isNotBlank()) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.login_button_text),
                fontSize = 24.sp,
            )
        }

    }
}



@Preview(showSystemUi = false, showBackground = false)
@Composable
fun LoginPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    BasicLogin(email, { email = it }, password, { password = it })
}