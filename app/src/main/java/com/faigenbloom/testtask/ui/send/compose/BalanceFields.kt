package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.text.DualStyleText
import com.faigenbloom.testtask.ui.common.text.MoneyTextTransformation
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import java.util.Currency

@Composable
fun BalanceFields(state: SendPageState) {
    val balanceText by remember { state.balanceState }
    val availableBalanceText by remember { state.availableBalanceState }
    val currency by remember { state.sendCurrencyState }
    DualStyleText(
        modifier = Modifier
            .fillMaxWidth(),
        firstPart = stringResource(R.string.send_funds_balance),
        firstStyle = typography.labelLarge.toSpanStyle().copy(
            color = colorScheme.onPrimary,
        ),
        secondPart = stringResource(
            R.string.send_funds_money_with_currency,
            currency.symbol,
            MoneyTextTransformation().format(balanceText),
        ),
        secondStyle = typography.labelLarge.toSpanStyle().copy(
            color = colorScheme.onBackground,
        ),
        textAlign = TextAlign.Center,
    )
    DualStyleText(
        modifier = Modifier
            .fillMaxWidth(),
        firstPart = stringResource(R.string.send_funds_available_balance),
        firstStyle = typography.labelLarge.toSpanStyle().copy(
            color = colorScheme.onPrimary,
        ),
        secondPart = stringResource(
            R.string.send_funds_money_with_currency,
            currency.symbol,
            MoneyTextTransformation().format(availableBalanceText),
        ),
        secondStyle = typography.labelLarge.toSpanStyle().copy(
            color = colorScheme.onBackground,
        ),
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
private fun BalanceFieldsPreview() {
    TestTaskTheme {
        Surface {
            Column {
                BalanceFields(
                    state = SendPageState(
                        sendCurrencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                        balanceState = remember { mutableStateOf("2340.00") },
                        availableBalanceState = remember { mutableStateOf("1700.50") },
                    )
                )
            }
        }
    }
}