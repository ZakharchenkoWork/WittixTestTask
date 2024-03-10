package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.animations.AnimateTabs
import com.faigenbloom.testtask.ui.send.CurrencyDialogState
import com.faigenbloom.testtask.ui.send.PurposeType
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import java.util.Currency
import java.util.Locale

@Composable
fun MainInfo(state: SendPageState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = colorScheme.primaryContainer,
            ),
    ) {
        AmountInputField(
            title = stringResource(R.string.send_funds_field_title_send),
            amountState = state.sendAmountState,
            currencyState = state.sendCurrencyState,
            errorState = state.sendErrorState,
            onCurrencyClicked = { state.onCurrencyChangeRequsted(true) },
            onAmountChanged = { state.onAmountChanged(true) },
        )
        BalanceFields(state = state)
        AmountInputField(
            title = stringResource(R.string.send_funds_field_title_reveive),
            amountState = state.receiveAmountState,
            currencyState = state.receiveCurrencyState,
            onCurrencyClicked = { state.onCurrencyChangeRequsted(false) },
            onAmountChanged = { state.onAmountChanged(false) },
        )
        ReceiveFields(state = state)
        ExchangeRatesHint()
        TransferOptions(state = state)
        AnimateTabs(state.isTransferRegularState.value) {
            Column {
                PurposeFields(state = state)
            }
        }

        Documents(state = state)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainInfoPreview() {
    TestTaskTheme {
        Surface {
            Column {
                MainInfo(
                    state = SendPageState(
                        sendAmountState = remember { mutableStateOf(TextFieldValue("100000.00")) },
                        sendCurrencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                        receiveAmountState = remember { mutableStateOf(TextFieldValue("358521.13")) },
                        receiveCurrencyState = remember { mutableStateOf(Currency.getInstance(ILS)) },
                        balanceState = remember { mutableStateOf("2340.00") },
                        availableBalanceState = remember { mutableStateOf("1700.50") },
                        exchangeRateState = remember { mutableStateOf("3.61206") },
                        reverseExchangeRateState = remember { mutableStateOf("0.28") },
                        isTransferRegularState = remember { mutableStateOf(false) },
                        expressPriceAmount = remember { mutableStateOf("50") },
                        purposeTypeState = remember { mutableStateOf(PurposeType.NONE) },
                        purposeTextState = remember { mutableStateOf(TextFieldValue()) },
                        isSaveBeneficiaryState = remember { mutableStateOf(true) },
                        isAgreedState = remember { mutableStateOf(true) },
                        onTransfer = {},
                        currencyDialogState = CurrencyDialogState(
                            availableCurrencies = listOf(),
                            isSend = false,
                            isCurrencyDialogVisibleState = false,
                            chosenCurrencyState = Currency.getInstance(Locale.getDefault()),
                            onCurrencyPicked = { },
                        ),
                        onCurrencyChangeRequsted = { },
                    ),
                )
            }
        }
    }
}
