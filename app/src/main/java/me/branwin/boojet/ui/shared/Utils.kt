package me.branwin.boojet.ui.shared

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

val NextNumberKeyboard = KeyboardOptions(
    keyboardType = KeyboardType.Number,
    imeAction = ImeAction.Next
)

val NextAlphaKeyboard = KeyboardOptions(
    keyboardType = KeyboardType.Text,
)

fun nextAction(fm: FocusManager): KeyboardActions {
    return KeyboardActions(
        onNext = { fm.moveFocus(FocusDirection.Down) }
    )
}

val DoneNumberKeyboard = KeyboardOptions(
    keyboardType = KeyboardType.Number,
    imeAction = ImeAction.Done
)

