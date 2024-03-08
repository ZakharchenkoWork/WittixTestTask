package com.faigenbloom.testtask.ui.send

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Currency
import java.util.Locale

class SendPageViewModel : ViewModel() {
    var onTransfer: () -> Unit = {}
    private fun onTransfer() {
        onTransfer()
    }

    private fun onCurrencyChangeRequsted(isForSend: Boolean) {
        _stateFlow.update {
            it.copy(
                currencyDialogState = it.currencyDialogState.copy(
                    isSend = isForSend,
                    isCurrencyDialogVisibleState = true,
                    chosenCurrencyState = if (isForSend) {
                        it.sendCurrencyState.value
                    } else {
                        it.receiveCurrencyState.value
                    },
                ),
            )
        }
    }

    private fun onCurrencyPicked(chosenCurrency: Currency) {
        val currencyState = if (state.currencyDialogState.isSend) {
            state.sendCurrencyState
        } else {
            state.receiveCurrencyState
        }
        currencyState.value = chosenCurrency

        _stateFlow.update {
            it.copy(
                currencyDialogState = it.currencyDialogState.copy(
                    isCurrencyDialogVisibleState = false,
                ),
            )
        }
    }

    private val state: SendPageState
        get() = _stateFlow.value
    private val _stateFlow = MutableStateFlow(
        SendPageState(
            onTransfer = ::onTransfer,
            onCurrencyChangeRequsted = ::onCurrencyChangeRequsted,
            currencyDialogState = CurrencyDialogState(
                onCurrencyPicked = ::onCurrencyPicked,
            ),
        ),
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        _stateFlow.update {
            it.copy(
                currencyDialogState = it.currencyDialogState.copy(
                    availableCurrencies = Currency.getAvailableCurrencies().toList(),
                ),
            )
        }
    }
}

data class SendPageState(
    val currencyDialogState: CurrencyDialogState = CurrencyDialogState(
        onCurrencyPicked = {},
    ),
    val sendAmountState: MutableState<String> = mutableStateOf(""),
    val sendCurrencyState: MutableState<Currency> = mutableStateOf(Currency.getInstance(Locale.getDefault())),
    val receiveAmountState: MutableState<String> = mutableStateOf(""),
    val receiveCurrencyState: MutableState<Currency> = mutableStateOf(Currency.getInstance(Locale.getDefault())),
    val balanceState: MutableState<String> = mutableStateOf(""),
    val availableBalanceState: MutableState<String> = mutableStateOf(""),
    val exchangeRateState: MutableState<String> = mutableStateOf(""),
    val reverseExchangeRateState: MutableState<String> = mutableStateOf(""),
    val isTransferRegularState: MutableState<Boolean> = mutableStateOf(false),
    val expressCostAmount: MutableState<String> = mutableStateOf(""),
    val purposeTypeState: MutableState<PurposeType> = mutableStateOf(PurposeType.NONE),
    val purposeTextState: MutableState<String> = mutableStateOf(""),
    val isSaveBeneficiaryState: MutableState<Boolean> = mutableStateOf(false),
    val isAgreedState: MutableState<Boolean> = mutableStateOf(false),
    val onCurrencyChangeRequsted: (Boolean) -> Unit,
    val onTransfer: () -> Unit = {},
)

data class CurrencyDialogState(
    val availableCurrencies: List<Currency> = listOf(),
    val isSend: Boolean = false,
    val isCurrencyDialogVisibleState: Boolean = false,
    val chosenCurrencyState: Currency = Currency.getInstance(Locale.getDefault()),
    val onCurrencyPicked: (chosenCurrency: Currency) -> Unit = {},
)

enum class PurposeType {
    NONE,
    ADVERTISING,
    EXPENSE,
    FINANCIAL,
    LEGAL,
    MEDICAL,
    PAYROLL,
    PROCESSING,
    TRADE,
    TRANSACTION,
    TRANSPORTATION,
    TRAVEL,
    PERSONAL,
    OTHER,
}
