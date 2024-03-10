package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.text.MoneyTextTransformation
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import java.util.Currency

@Composable
fun ReceiveFields(state: SendPageState) {
    val sendCurrency by remember { state.sendCurrencyState }
    val receiveCurrency by remember { state.receiveCurrencyState }
    val exchangeRateState by remember { state.exchangeRateState }
    val reverseExchangeRateState by remember { state.reverseExchangeRateState }
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(R.string.send_funds_exchange_label),
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge,
    )
    RatesText(
        firstCurrency = sendCurrency,
        secondCurrency = receiveCurrency,
        firstRate = MoneyTextTransformation().format(exchangeRateState),
        secondRate = MoneyTextTransformation().format(reverseExchangeRateState),
    )
}

@Composable
private fun RatesText(
    firstCurrency: Currency,
    secondCurrency: Currency,
    firstRate: String,
    secondRate: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(
            R.string.send_funds_exchange_rates,
            firstCurrency.currencyCode,
            secondCurrency.currencyCode,
            firstRate,
            secondRate,
        ),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReceiveFieldsPreview() {
    TestTaskTheme {
        Surface {
            Column {
                ReceiveFields(
                    state = SendPageState(
                        sendCurrencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                        receiveCurrencyState = remember { mutableStateOf(Currency.getInstance(ILS)) },
                        exchangeRateState = remember { mutableStateOf("3.61206") },
                        reverseExchangeRateState = remember { mutableStateOf("0.28") },
                    )
                )
            }
        }
    }
}
