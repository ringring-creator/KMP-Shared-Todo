package ui.editTodo

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

private val timeZone = TimeZone.currentSystemDefault()

fun LocalDate.toDeadline(): EditTodoUiState.Deadline = EditTodoUiState.Deadline(
    milliseconds = atStartOfDayIn(timeZone).toEpochMilliseconds()
)

fun EditTodoUiState.Deadline.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochMilliseconds(milliseconds)
    return instant.toLocalDateTime(timeZone).date
}


