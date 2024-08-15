package org.akai.sciclubhub.ui.components.navigationbottombar

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.akai.sciclubhub.R
import org.akai.sciclubhub.ui.components.PlaceHolderView

@Composable
fun NavigationBottomBar(
    navController: NavHostController,
    onClickActions: List<() -> Unit>
) {
    val context = LocalContext.current
    var focusedIndex by rememberSaveable { mutableIntStateOf(1) }
    val iconList = iconList(context)

    val colorSchema  = MaterialTheme.colorScheme
    var maxWidth by rememberSaveable { mutableFloatStateOf(0F) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                maxWidth = size.width
            }) {
    }

    val firstBlockWidth by animateFloatAsState (
        targetValue = maxWidth / iconList.size * focusedIndex,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 500
        ),
        label = "firstBlockWidth"
    )
    val secondBlockWidth by animateFloatAsState (
        targetValue =  - (maxWidth / iconList.size * (3 - focusedIndex)),
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 500
        ),
        label = "secondBlockWidth"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .drawBehind {
                maxWidth = size.width
                drawRoundRect(
                    color = colorSchema.primary,
                    cornerRadius = CornerRadius(
                        x = 35.dp.toPx(),
                        y = 35.dp.toPx()
                    ),
                    topLeft = Offset(
                        x = 30F,
                        y = 2.dp.toPx()
                    ),
                    size = Size(
                        width = firstBlockWidth,
                        height = size.height - 4.dp.toPx()
                    )
                )

                drawRoundRect(
                    color = colorSchema.primary,
                    cornerRadius = CornerRadius(
                        x = 35.dp.toPx(),
                        y = 35.dp.toPx()
                    ),
                    topLeft = Offset(
                        x = size.width - 30F,
                        y = 2.dp.toPx()
                    ),
                    size = Size(
                        width = secondBlockWidth,
                        height = size.height - 4.dp.toPx()
                    )
                )
            },
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        iconList.forEachIndexed { index, icon ->
            val iconColor by animateColorAsState(
                targetValue = if (focusedIndex == index)
                    colorSchema.onPrimaryContainer
                else
                    colorSchema.primaryContainer,
                animationSpec = tween(
                    easing = LinearEasing,
                    durationMillis = 500
                ),
                label = "bottomBarIconColor",
            )

            val focusBackgroundIndicatorSize by animateFloatAsState(
                targetValue = if (focusedIndex == index) 100F else 0F,
                animationSpec = tween(
                    easing = LinearEasing,
                    durationMillis = 500
                ),
                label = "focusedIconBackgroundIndicatorSize"
            )
            IconButton(
                modifier = Modifier
                    .size(size = 70.dp),
                onClick = {
                    focusedIndex = index

                    if (index < onClickActions.size)
                        onClickActions[index]()

                    navController.navigate(icon.destination)
                }
            )  {
                Icon(
                    modifier = Modifier
                        .size(size = 45.dp)
                        .drawBehind {
                            drawCircle(
                                color = colorSchema.primaryContainer,
                                radius = focusBackgroundIndicatorSize
                            )
                        },
                    imageVector = if (focusedIndex == index) icon.selectedIcon else icon.unselectedIcon,
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}

private fun iconList(context: Context) = listOf(
    BottomBarIcon(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        destination = context.getString(R.string.user_account_view_destination)
    ),
    BottomBarIcon(
        selectedIcon = Icons.Filled.Inbox,
        unselectedIcon = Icons.Outlined.Inbox,
        destination = context.getString(R.string.home_view_destination)
    ),
    BottomBarIcon(
        selectedIcon = Icons.Filled.ChatBubble,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        destination = context.getString(R.string.chat_view_destination)
    ),
    BottomBarIcon(
        selectedIcon = Icons.Filled.CalendarToday,
        unselectedIcon = Icons.Outlined.CalendarToday,
        destination = context.getString(R.string.calendar_view_destination)
    )
)

@Preview
@Composable
fun NavigationBottomBarPreview() {
    Scaffold(
        bottomBar = {
            NavigationBottomBar(
                navController = NavHostController(LocalContext.current),
                onClickActions = listOf(
                    {}, {}, {}, {}
                )
            )
        }
    ) { innerPadding ->
        PlaceHolderView(title = innerPadding.toString())
    }
}
