@file:OptIn(ExperimentalMaterial3Api::class)

package me.branwin.boojet.ui.tipcalculator

import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.branwin.boojet.R
import java.text.NumberFormat

@Composable
fun TipCalculatorScreen() {
    val focusManager = LocalFocusManager.current

    val calcTipStr = stringResource(R.string.calculate_tip)

    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tipInput by remember { mutableStateOf("") }
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    var roundUp by remember { mutableStateOf(false) }


    val tip = calculateTip(amount, tipPercent, roundUp)


    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            calcTipStr,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditNumberField(
            R.string.bill_amount,
            amountInput,
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            KeyboardActions(
                onNext =  { focusManager.moveFocus(FocusDirection.Down) }
            )
        ) {
            amountInput = it
        }
        EditNumberField(
            R.string.how_was_the_service,
            tipInput,
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        ) {
            tipInput = it
        }
        RoundTipRow(roundUp) {
            roundUp = it
        }
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

//        DbTestRow(viewModel)
    }
}
//
//@Composable
//fun DbTestRow(viewModel: MainViewModel) {
//    Column(modifier = Modifier.padding(32.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        DbInsertRow(viewModel)
//        DbReadRow(viewModel.allCategories.collectAsState().value)
//    }
//}
//
//@Composable
//fun DbInsertRow(viewModel: MainViewModel) {
//    val focusManager = LocalFocusManager.current
//    var catName by remember { mutableStateOf("") }
//    EditNumberField(
//        R.string.text_expense_amount,
//        catName,
//        KeyboardOptions(imeAction = ImeAction.Done),
//        KeyboardActions(onDone = {
//            focusManager.clearFocus()
//            viewModel.insertCategory(Category(name = catName))
//        })
//    ) {
//        catName = it
//    }
//}
//
//@Composable
//fun DbReadRow(categories: List<Category>?) {
//    LazyColumn(
//        Modifier
//            .fillMaxWidth()
//            .padding(10.dp)) {
//        item {
//            Text("Category Name")
//        }
//
//        categories?.let {
//            items(categories) {
//                Text(it.name)
//            }
//        }
//    }
//}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value,
        textStyle = TextStyle(textAlign = TextAlign.End),
        label = { Text(stringResource(label), modifier = Modifier.fillMaxWidth()) },
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
    )
}

@Composable
fun RoundTipRow(roundUp: Boolean, modifier: Modifier = Modifier, onRoundUpChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(R.string.round_up_tip))
        Switch(
            roundUp,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            ),
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean = false): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}