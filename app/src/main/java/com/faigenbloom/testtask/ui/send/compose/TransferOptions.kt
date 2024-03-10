package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.text.MoneyTextTransformation
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import java.util.Currency

@Composable
fun TransferOptions(state: SendPageState) {
    var isTransferRegular by remember { state.isTransferRegularState }
    val expressCostAmount by remember { state.expressPriceAmount }
    val sendCurrency by remember { state.sendCurrencyState }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 13.dp,
            ),
        text = stringResource(id = R.string.send_funds_transfer_options_title),
        color = colorScheme.onBackground,
        style = typography.bodyMedium,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        OptionButton(
            modifier = Modifier
                .weight(0.5f),
            text = stringResource(R.string.send_funds_transfer_options_regular),
            isChecked = isTransferRegular,
            onClick = {
                isTransferRegular = true
                state.onTransferOptionsChanged()
            },
        )
        OptionButton(
            modifier = Modifier
                .weight(0.5f),
            text = stringResource(
                R.string.send_funds_transfer_options_express,
                stringResource(
                    R.string.send_funds_money_with_currency,
                    sendCurrency.symbol,
                    MoneyTextTransformation().format(expressCostAmount),
                ),
            ),
            isChecked = isTransferRegular.not(),
            onClick = {
                isTransferRegular = false
                state.onTransferOptionsChanged()
            },
        )
    }
}

@Composable
private fun OptionButton(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = if (isChecked) {
                    colorScheme.secondaryContainer
                } else {
                    colorScheme.primaryContainer
                },
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            color = if (isChecked) {
                colorScheme.secondaryContainer
            } else {
                colorScheme.onBackground
            },
            textAlign = TextAlign.Center,
            style = typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferOptionsPreview() {
    TestTaskTheme {
        Surface {
            Column {
                TransferOptions(
                    state = SendPageState(
                        isTransferRegularState = remember { mutableStateOf(false) },
                        expressPriceAmount = remember { mutableStateOf("50") },
                        sendCurrencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                    )
                )
            }
        }
    }
}
