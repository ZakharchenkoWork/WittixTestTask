@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask.ui.send

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.CurrencyPicker
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.TopBar
import com.faigenbloom.testtask.ui.common.animations.Loading
import com.faigenbloom.testtask.ui.common.animations.Success
import com.faigenbloom.testtask.ui.send.compose.MainInfo
import com.faigenbloom.testtask.ui.send.compose.SendingOptions
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

@Composable
fun SendPage(
    modifier: Modifier = Modifier,
    state: SendPageState,
) {
    val listState = rememberLazyListState()
    var sendErrorState by remember { state.animateErrorState }
    val coroutineScope = rememberCoroutineScope()
    if (sendErrorState) {
        LaunchedEffect("ScrollAnimationKey") {
            coroutineScope.launch {
                listState.animateScrollToItem(0, 0)
                sendErrorState = false
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
    ) {
        item {
            TopBar(stringResource(R.string.send_funds_title))
            Header(stringResource(R.string.send_funds_header))
            MainInfo(state = state)
            SendingOptions(state = state)
            TransferButton(state = state)
        }
    }
    CurrencyDialog(state = state.currencyDialogState)
    Loading(state.isLoadingState)
    Success(state.successState)
}

@Composable
private fun Header(title: String) {
    Text(
        modifier = Modifier
            .padding(top = 6.dp)
            .fillMaxWidth(),
        text = title,
        color = colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = typography.bodyMedium,
    )
}

@Composable
fun TransferButton(state: SendPageState) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.tertiaryContainer,
        ),
        onClick = state.onTransfer,
    ) {
        Text(
            modifier = Modifier
                .padding(end = 16.dp),
            text = stringResource(R.string.send_funds_transfer),
            color = colorScheme.onTertiaryContainer,
            fontWeight = FontWeight.Medium,
            style = typography.titleSmall,
        )
    }
}

@Composable
private fun CurrencyDialog(state: CurrencyDialogState) {
    if (state.isCurrencyDialogVisibleState) {
        CurrencyPicker(
            currencies = state.availableCurrencies,
            chosenCurrency = state.chosenCurrencyState,
            onCurrencyPicked = state.onCurrencyPicked,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SendPagePreview() {
    TestTaskTheme {
        Surface {
            SendPage(
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
