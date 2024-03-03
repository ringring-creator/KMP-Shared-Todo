package com.ring.ring.kmpsharedtodo.ui.editTodo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kmp_shared_ui_todo.composeapp.generated.resources.Res
import kmp_shared_ui_todo.composeapp.generated.resources.edit_screen_title
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

data class EditTodoUiState(
    val title: String,
    val description: String,
    val done: Boolean,
    val deadline: Deadline,
    val showDatePicker: Boolean,
) {
    data class Deadline(val milliseconds: Long) {
        companion object {
            fun createCurrentDate(): Deadline {
                return Deadline(
                    milliseconds = Clock.System.now().toEpochMilliseconds()
                )
            }
        }

        override fun toString(): String = toLocalDate().run {
            "${year}-${month}-${dayOfMonth}"
        }
    }
}

interface EditTodoStateUpdater {
    fun setTitle(title: String) {}
    fun setDescription(description: String) {}
    fun setDone(done: Boolean) {}
    fun setDeadline(deadline: Long) {}
    fun dismissDatePicker() {}
    fun showDatePicker() {}
    fun save() {}
    fun delete() {}
    fun onBack() {}
}

class EditTodoScreen(val id: Long?) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<EditTodoScreenModel>(factory = {
            get { parametersOf(id) }
        })
        val navigator = LocalNavigator.currentOrThrow
        val uiState: EditTodoUiState = rememberEditTodoUiState(
            popBackStack = { navigator.pop() },
            viewModel = screenModel
        )
        EditTodoScreen(uiState, screenModel)
    }

    @Composable
    private fun rememberEditTodoUiState(
        viewModel: EditTodoScreenModel,
        popBackStack: () -> Boolean,
    ): EditTodoUiState {
        LaunchedEffect(Unit) {
            viewModel.backEvent.collect {
                popBackStack()
            }
        }

        return EditTodoUiState(
            viewModel.title.collectAsState().value,
            viewModel.description.collectAsState().value,
            viewModel.done.collectAsState().value,
            viewModel.deadline.collectAsState().value,
            viewModel.showDatePicker.collectAsState().value,
        )
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun EditTodoScreen(
        editTodoUiState: EditTodoUiState,
        stateUpdater: EditTodoStateUpdater,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.edit_screen_title)) },
                    navigationIcon = {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { stateUpdater.onBack() }
                        )
                    }
                )
            },
            floatingActionButton = {
                Column {
                    FloatingActionButton(
                        onClick = stateUpdater::save,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Create,
                            contentDescription = "create",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    FloatingActionButton(
                        onClick = stateUpdater::delete,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "delete",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        ) {
            EditTodoContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                uiState = editTodoUiState,
                stateUpdater = stateUpdater,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EditTodoContent(
        modifier: Modifier,
        uiState: EditTodoUiState,
        stateUpdater: EditTodoStateUpdater,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = uiState.done, onCheckedChange = stateUpdater::setDone)
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.title,
                        onValueChange = stateUpdater::setTitle,
                        label = { Text("title") }
                    )
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = uiState.description,
                    onValueChange = stateUpdater::setDescription,
                    label = { Text("description") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable(onClick = stateUpdater::showDatePicker),
                ) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "dateRange",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(uiState.deadline.toString())
                }
            }
            DeadlineDatePicker(
                deadline = uiState.deadline,
                showDatePicker = uiState.showDatePicker,
                dismiss = stateUpdater::dismissDatePicker,
                setDate = stateUpdater::setDeadline,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DeadlineDatePicker(
        deadline: EditTodoUiState.Deadline,
        showDatePicker: Boolean,
        datePickerState: DatePickerState = rememberDatePickerState(
            initialSelectedDateMillis = deadline.milliseconds
        ),
        dismiss: () -> Unit,
        setDate: (Long) -> Unit,
    ) {
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = dismiss,
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { setDate(it) }
                            dismiss()
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = dismiss) {
                        Text(text = "CANCEL")
                    }
                },
                modifier = Modifier,
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}