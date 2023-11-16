@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.tranquiljot.ui.screens.entryScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tranquiljot.ui.screens.AppViewModelInitializer
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryScreen(
    viewModel: EntryScreenViewModel = viewModel(factory = AppViewModelInitializer.Factory),
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember {
        FocusRequester()
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val time = LocalDateTime.now()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = fadeOut()
    ) {

        Column(modifier = Modifier
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton {
                    navController.navigateUp()
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.End)
                ) {
                    SaveButton {
                        keyboardController?.hide()
                        focusManager.clearFocus(force = true)
                        coroutineScope.launch {
                            viewModel.saveNote()
                        }
                        navController.navigateUp()
                    }
                    Box {
                        MenuTagsButton {
                            expanded = true
                        }
                        if (expanded) {
                            MenuList(expanded = expanded, dismissMenu = { expanded = false})
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TitleField(noteDetails = viewModel.noteUiState.noteDetails, modifier = Modifier, focusRequester = focusRequester) {
                viewModel.updateUiState(it)
            }
            Text(
                text = "${time.dayOfMonth} ${getMonth(time.monthValue)} ${time.hour}:${time.minute}",
                modifier = Modifier.padding(vertical = 4.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            NoteField(noteDetails = viewModel.noteUiState.noteDetails, modifier = Modifier.fillMaxSize()) {
                viewModel.updateUiState(it)
            }
        }
    }
}

@Composable
fun NoteField(noteDetails: NoteDetails, modifier: Modifier, noteUpdate: (NoteDetails) -> Unit) {
    var note by rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = note,
        onValueChange = {
            noteUpdate(
                noteDetails.copy(
                    note = it
                )
            )
            note = it
        },
        label = { Text(text = "Note") },
        modifier = modifier.fillMaxWidth(),
        singleLine = false,
        shape = RoundedCornerShape(16.dp),
    )

}
@Composable
fun TitleField(
    noteDetails: NoteDetails,
    modifier: Modifier,
    focusRequester: FocusRequester,
    titleUpdate: (NoteDetails) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = title,
        onValueChange = {
            titleUpdate(
                noteDetails.copy(
                    title = it
                )
            )
            title = it
        },
        label = { Text(text = "Title") },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        singleLine = true,
        shape = RoundedCornerShape(25),
    )
}

@Composable
fun MenuList(expanded: Boolean, dismissMenu: () -> Unit) {
    DropdownMenu(expanded = expanded, onDismissRequest = { dismissMenu() }) {
        DropdownMenuItem(
            text = {
                   Text(text = "Tags")
            },
            onClick = {

            }
        )
    }
}
@Composable
fun MenuTagsButton(showTags: () -> Unit) {
    FilledIconButton(
        onClick = {
            showTags()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Menu"
        )
    }
}
@Composable
fun BackButton(backToHome: () -> Unit) {
    FilledIconButton(
        onClick = {
            backToHome()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Menu"
        )
    }
}
@Composable
fun SaveButton(saveNote: () -> Unit) {
    FilledTonalIconButton(
        onClick = {
            saveNote()
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Done,
            contentDescription = "Save"
        )
    }
}

/*
@Composable
fun ShareButton(shareNote: () -> Unit) {
    FilledTonalIconButton(
        onClick = {
            shareNote()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_share_24),
            contentDescription = "Share"
        )
    }
}
*/
private fun getMonth(mon: Int) : String {
    return when (mon) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        else -> "December"
    }
}











