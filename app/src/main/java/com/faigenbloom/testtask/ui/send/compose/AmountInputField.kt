package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.containerColor
import com.faigenbloom.testtask.ui.common.getFlag
import com.faigenbloom.testtask.ui.common.text.BaseTextField
import com.faigenbloom.testtask.ui.common.text.MoneyTextTransformation
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import com.faigenbloom.testtask.ui.theme.tint
import java.util.Currency

@Composable
fun AmountInputField(
    title: String,
    amountState: MutableState<TextFieldValue>,
    currencyState: MutableState<Currency>,
    errorState: MutableState<Boolean>? = null,
    onCurrencyClicked: () -> Unit,
    onAmountChanged: () -> Unit,
) {
    var sendAmountText by remember { amountState }
    val sendCurrency by remember { currencyState }
    val isError by remember { errorState ?: mutableStateOf(false) }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp, start = 13.dp),
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyMedium,
    )
    Row(
        modifier = Modifier
            .padding(all = 13.dp)
            .fillMaxWidth()
            .height(64.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = containerColor(
                    isError = isError,
                    isChecked = sendAmountText.text.isNotBlank(),
                ),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BaseTextField(
            modifier = Modifier
                .weight(0.6f)
                .padding(all = 8.dp),
            value = sendAmountText,
            textStyle = MaterialTheme.typography.titleLarge,
            color = if (isError.not()) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onError
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = MoneyTextTransformation(),
            onValueChange = {
                sendAmountText = it
                onAmountChanged()
            },
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.send_funds_amount),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 7.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        )
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f)
                .clickable { onCurrencyClicked() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 11.dp)
                    .size(24.dp),
                painter = painterResource(id = sendCurrency.getFlag()),
                contentDescription = "",
            )
            Text(
                modifier = Modifier,
                text = sendCurrency.currencyCode,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            Icon(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.icon_dropdown),
                tint = MaterialTheme.colorScheme.tint(),
                contentDescription = "",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountInputFieldSendEmptyPreview() {
    TestTaskTheme {
        Surface {
            Column {
                AmountInputField(
                    title = stringResource(R.string.send_funds_field_title_reveive),
                    amountState = remember { mutableStateOf(TextFieldValue("")) },
                    currencyState = remember { mutableStateOf(Currency.getInstance(ILS)) },
                    onCurrencyClicked = {},
                    onAmountChanged = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountInputFieldSendErrorPreview() {
    TestTaskTheme {
        Surface {
            Column {
                AmountInputField(
                    title = stringResource(R.string.send_funds_field_title_send),
                    amountState = remember { mutableStateOf(TextFieldValue("100000.00")) },
                    currencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                    errorState = remember { mutableStateOf(true) },
                    onCurrencyClicked = {},
                    onAmountChanged = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountInputFieldReceivePreview() {
    TestTaskTheme {
        Surface {
            Column {
                AmountInputField(
                    title = stringResource(R.string.send_funds_field_title_send),
                    amountState = remember { mutableStateOf(TextFieldValue("358521.13")) },
                    currencyState = remember { mutableStateOf(Currency.getInstance(ILS)) },
                    onCurrencyClicked = {},
                    onAmountChanged = {},
                )
            }
        }
    }
}