package com.faigenbloom.testtask.ui.send

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Currency
import java.util.Locale

class SendPageViewModel : ViewModel() {
    var onTransfer: () -> Unit = {}
    private fun onTransfer() {
    }

    private val state: SendPageState
        get() = _stateFlow.value
    private val _stateFlow = MutableStateFlow(
        SendPageState(
            onTransfer = ::onTransfer,
        ),
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
    }
}

data class SendPageState(
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
    val onTransfer: () -> Unit,
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
