@file:OptIn(ExperimentalAnimationApi::class)

package com.example.tranquiljot.ui.screens.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tranquiljot.R
import com.example.tranquiljot.model.Notes
import com.example.tranquiljot.ui.screens.AppViewModelInitializer


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelInitializer.Factory),
    navigateEntry: () -> Unit,
    navigateDetails: (Int) -> Unit,
) {
    val homeUiState: HomeUiState by viewModel.homeUiState.collectAsState()
    Scaffold (
        topBar = {
            AppBar()
        },
        floatingActionButton = {
            HomeFloatingActionButton {
                navigateEntry()
            }
        },
        modifier = Modifier.padding(16.dp)
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            FilterRow()
            HomeBody(noteList = homeUiState.notes) {
                navigateDetails(it)
            }
        }
    }
}

/**
 *
 *  Body of the home screen
 * */
@Composable
fun HomeBody(noteList: List<Notes>, navigateDetails: (Int) -> Unit = {}) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(visibleState = visibleState, enter = fadeIn(
        animationSpec = spring(
            dampingRatio = DampingRatioLowBouncy,
        )
    ), exit = fadeOut()
    ) {
        LazyColumn {
            noteList.forEachIndexed { index, note ->
                item {
                    NoteCard(modifier = Modifier.animateEnterExit(
                        enter = slideInVertically (
                            animationSpec = spring(
                                dampingRatio = DampingRatioLowBouncy,
                                stiffness = StiffnessVeryLow
                            ),
                            initialOffsetY = {it * (index + 1)},
                        )), note = note) {
                        navigateDetails(note.id)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteCard(modifier: Modifier, note: Notes,navigateDetails: (Int) -> Unit = {}) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorScheme.primaryContainer,
        ),
        shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateDetails(note.id)
                }
                .padding(16.dp),
        ) {
            Text(text = note.title, color = colorScheme.onPrimaryContainer)
            Text(text = note.note, color = colorScheme.onPrimaryContainer)
            Text(text = note.time, color = colorScheme.onPrimaryContainer)
        }
    }
}
/**
 *  Floating Action Button
 * */

@Composable
fun HomeFloatingActionButton(toEntry: () -> Unit) {
    FloatingActionButton(
        onClick = {
            toEntry()
        },
        containerColor = colorScheme.inversePrimary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create"
        )
    }
}

// filter row for the home screen
@Composable
fun FilterRow() {
    var isFiltering by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = {
                isFiltering = true
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = "Menu"
            )
        }
    }
    if (isFiltering) {
            AlertDialog(
                onDismissRequest = { isFiltering = false },
                icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
                title = {
                    Text(text = "Title")
                },
                text = {
                    Text(
                        "This area typically contains the supportive text " +
                                "which presents the details regarding the Dialog's purpose."
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isFiltering = false
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isFiltering = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
    }
}


// App bar for the home screen
@Composable
fun AppBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundIcon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Tranquil Jot", fontSize = 24.sp)
    }
}

/**
 *  A round icon with a white background
 * */
@Composable
fun RoundIcon() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .size(50.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(painter = painterResource(id = R.drawable.notes), contentDescription = "Notes logo")      // TODO: Add logo
    }
}






