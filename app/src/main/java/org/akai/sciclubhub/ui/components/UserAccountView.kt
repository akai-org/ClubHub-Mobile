package org.akai.sciclubhub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.akai.sciclubhub.data.UUID
import org.akai.sciclubhub.data.club.Club
import org.akai.sciclubhub.data.club.dummyClub
import org.akai.sciclubhub.data.user.DummyUser
import org.akai.sciclubhub.data.user.User

@Composable
fun UserAccountView(
    user: User?,
    usersClubs: List<Club>,
    usersProjects: List<UUID>,
    padding: PaddingValues,
) {
    Column {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .weight(weight = 1f, fill = false),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TitledCard(title = "Account") {
                Column {
                    Row {
                        Card(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(120.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = "Person Icon",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(100.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = (user?.name ?: "Loading...") + " " + (user?.surname ?: ""),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )

                            Text(
                                text = user?.uniList?.first()?.name ?: "Loading...",
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                }
            }
            val clubsCoroutine = rememberCoroutineScope()

            TitledCard(title = "Clubs") {
                LazyRow {
                    items(usersClubs) {
                        Card (
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(100.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 14.sp,
                                    maxLines = 2,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            TitledCard(title = "Projects") {
                LazyRow {
                    items(usersClubs) {
                        Card (
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(100.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 14.sp,
                                    maxLines = 2,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(76.dp))
        }
    }
}

@Preview
@Composable
fun UserAccountViewPreview() {
    val dummyUser = User.DummyUser()
    val dummyClubsList = listOf(dummyClub)

    UserAccountView(user = dummyUser, dummyClubsList, emptyList(), padding = PaddingValues(0.dp))
}
