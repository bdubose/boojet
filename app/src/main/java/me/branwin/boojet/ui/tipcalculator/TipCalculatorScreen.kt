package me.branwin.boojet.ui.tipcalculator

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.branwin.boojet.R
import me.branwin.boojet.ui.shared.EditNumberField
import java.text.NumberFormat

@Composable
fun TipCalculatorScreen() {
    val focusManager = LocalFocusManager.current
    val currencyFormat = NumberFormat.getCurrencyInstance()

    val calcTipStr = stringResource(R.string.calculate_tip)

    var amountInput by remember { mutableStateOf("0") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tipInput by remember { mutableStateOf("20") }
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    var roundUpTip by remember { mutableStateOf(false) }
    var roundUpTotal by remember { mutableStateOf(false) }


    val tip: Double
    val total: Double

    if (roundUpTotal) {
        // calculate original total rounded up, then update the tip
        val originalTip = calculateTip(amount, tipPercent, roundUpTip)
        total = calculateTotal(amount, originalTip, roundUpTotal)
        tip = total - amount
    } else {
        tip = calculateTip(amount, tipPercent, roundUpTip)
        total = calculateTotal(amount, tip, roundUpTotal)
    }

    // This causes NaNs, but will update the tip % input correct when we have values
    //val decimalFormat = DecimalFormat("##.##")
    //tipInput = decimalFormat.format((tip / amount) * 100)

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
        RoundTipRow(roundUpTip, true) {
            roundUpTip = it
            if (it) roundUpTotal = false
        }
        RoundTipRow(roundUpTotal, false) {
            roundUpTotal = it
            if (it) roundUpTip = false
        }
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.tip_amount, currencyFormat.format(tip)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
       Text(
            stringResource(R.string.tip_total, currencyFormat.format(total)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun RoundTipRow(roundUp: Boolean, isTip: Boolean, modifier: Modifier = Modifier, onRoundUpChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(if (isTip) R.string.round_up_tip else R.string.round_up_total))
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
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUpTip: Boolean = false): Double {
    var tip = tipPercent / 100 * amount
    if (roundUpTip) tip = kotlin.math.ceil(tip)
    return tip
}

fun calculateTotal(amount: Double, tip: Double, roundUpTotal: Boolean = false): Double {
    var total = amount + tip
    if (roundUpTotal) total = kotlin.math.ceil(total)
    return total
}

@Preview
@Composable
fun Preview() {
    TipCalculatorScreen()
}
