package org.akai.sciclubhub.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.akai.sciclubhub.R

@Composable
fun OutlinedEmailField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = value.length < 8,
    trailingIcon: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = "Person",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    ) {

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = { Text(stringResource(id = R.string.email_text_field_label)) },
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(25.dp)
    )
}

@Composable
@Preview
fun OutlinedEmailFieldPreview() {
    OutlinedEmailField(value = "", onValueChange = {})
}