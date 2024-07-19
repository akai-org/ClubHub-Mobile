package org.akai.sciclubhub.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.akai.sciclubhub.R

@Composable
fun OutlinedPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = value.isNotBlank() && !value.matches(Regex(".+@.+\\..{2,6}")),
    trailingIcon: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.Rounded.Lock,
            contentDescription = "Closed Lock",
            tint = MaterialTheme.colorScheme.primary
        )
    }
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.password_text_field_label)) },
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        shape = RoundedCornerShape(25.dp),
    )
}

@Composable
@Preview
fun OutlinedPasswordFieldPreview() {
    OutlinedPasswordField(value = "", onValueChange = {})
}