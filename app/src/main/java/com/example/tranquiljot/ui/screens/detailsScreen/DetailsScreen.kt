@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.tranquiljot.ui.screens.detailsScreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tranquiljot.R
import com.example.tranquiljot.ui.screens.entryScreen.BackButton
import com.example.tranquiljot.ui.screens.entryScreen.NoteDetails
import com.example.tranquiljot.ui.screens.entryScreen.SaveButton
import kotlinx.coroutines.launch


@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    viewModel: DetailsScreenViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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
                navHostController.navigateUp()
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.End)
            ) {
                ShareButton {

                }
                SaveButton {
                    keyboardController?.hide()
                    coroutineScope.launch {
                        viewModel.updateNote()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        DetailTitleField(noteDetails = viewModel.noteUiState.noteDetails, modifier = Modifier) {
            viewModel.updateUiState(it)
        }

        Spacer(modifier = Modifier.height(8.dp))
        DetailsNoteField(noteDetails = viewModel.noteUiState.noteDetails, modifier = Modifier.fillMaxSize()) {
            viewModel.updateUiState(it)
        }
    }
}
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
@Composable
fun DetailTitleField(
    noteDetails: NoteDetails,
    modifier: Modifier,
    titleUpdate: (NoteDetails) -> Unit
) {
    val text : String = noteDetails.title
    var title by rememberSaveable {
        mutableStateOf(text)
    }
    OutlinedTextField(
        value = noteDetails.title,
        onValueChange = {
            titleUpdate(
                noteDetails.copy(
                    title = it
                )
            )
            title = it
        },
        label = {
            Text(text = "Title")
        },
        modifier = modifier
            .fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(25),
    )
}
@Composable
fun DetailsNoteField(noteDetails: NoteDetails, modifier: Modifier, noteUpdate: (NoteDetails) -> Unit) {
    var note by rememberSaveable {
        mutableStateOf(noteDetails.title)
    }
    OutlinedTextField(
        value = noteDetails.note,
        onValueChange = {
            noteUpdate(
                noteDetails.copy(
                    note = it
                )
            )
            note = it
        },
        label = {
                Text(text = "Note")
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = false,
        shape = RoundedCornerShape(16.dp),
    )
}