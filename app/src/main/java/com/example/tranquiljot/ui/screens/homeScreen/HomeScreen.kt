package com.example.tranquiljot.ui.screens.homeScreen

import androidx.compose.runtime.remember
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.tranquiljot.ui.screens.AppViewModelInitializer


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelInitializer.Factory),
    navigateEntry: () -> Unit,
    navigateDetails: (Int) -> Unit,
) {
    Scaffold (
        topBar = {
            AppBar()
        },
        modifier = Modifier.padding(16.dp)
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            FilterRow()
        }
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