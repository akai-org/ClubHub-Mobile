package org.akai.sciclubhub.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitledCard(
    title: String,
    content: @Composable () -> Unit
) {
   Card (
       modifier = Modifier
           .fillMaxWidth()
           .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
       shape = RoundedCornerShape(16.dp),
       colors = CardColors(
           containerColor = MaterialTheme.colorScheme.primary,
           contentColor = Color.Black,
           disabledContainerColor = Color.Gray,
           disabledContentColor = Color.DarkGray
       )
   )
   {
       HorizontalDivider(
           modifier = Modifier
               .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
               .fillMaxWidth(),
           color = MaterialTheme.colorScheme.onPrimary,
           thickness = 2.dp
       )
       Text(
           text = title,
           modifier = Modifier.padding(start = 16.dp),
           color = MaterialTheme.colorScheme.onPrimary,
           style = MaterialTheme.typography.titleLarge,
           maxLines = 1,

       )
       Box(
           modifier = Modifier.padding(16.dp)
       ) {
           content()
       }
   }

}

@Preview
@Composable
fun TitledCardPreview() {
    TitledCard(title = "Title") {
    }
}