package com.faigenbloom.testtask.ui.send

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faigenbloom.testtask.domain.balance.GetBalanceUseCase
import com.faigenbloom.testtask.domain.currency.GetAllCurrenciesUseCase
import com.faigenbloom.testtask.domain.currency.GetMainCurrencyUseCase
import com.faigenbloom.testtask.domain.currency.GetReceiverCurrencyUseCase
import com.faigenbloom.testtask.domain.exchange.GetExchangeRatesUseCase
import com.faigenbloom.testtask.domain.transfer.GetTransferPriceUseCase
import com.faigenbloom.testtask.ui.common.animations.AnimationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

class SendPageViewModel(
    private val getMainCurrencyUseCase: GetMainCurrencyUseCase,
    private val getReceiverCurrencyUseCase: GetReceiverCurrencyUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val getTransferPriceUseCase: GetTransferPriceUseCase,
) : ViewModel() {
    var onTransfer: () -> Unit = {}
    var onDocumentPickerRequested: () -> Unit = {}
    fun onDocumentLoaded(uri: Uri) {
        _stateFlow.update {
            it.copy(
                successState = it.successState.copy(
                    isShown = true,
                ),
            )
        }
    }

    private fun onDocumentPicker() {
        onDocumentPickerRequested()
    }

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
        viewModelScope.launch {
            updateCurrencies()
        }
    }

    private suspend fun updateCurrenciesInDialog() {
        _stateFlow.update {
            it.copy(
                currencyDialogState = it.currencyDialogState.copy(
                    availableCurrencies = getAllCurrenciesUseCase(it.sendCurrencyState.value),
                ),
            )
        }
    }

    private suspend fun updateTransferPrices() {
        state.expressPriceAmount.value = getTransferPriceUseCase()
    }

    private suspend fun reloadCurrencies() {
        state.sendCurrencyState.value = getMainCurrencyUseCase()
        state.receiveCurrencyState.value = getReceiverCurrencyUseCase()
    }

    private suspend fun prepareBalances() {
        val balanceModel = getBalanceUseCase()
        state.balanceState.value = balanceModel.total
        state.availableBalanceState.value = balanceModel.available
    }

    private suspend fun updateCurrencies() {
        val exchangeRates = getExchangeRatesUseCase(
            state.sendCurrencyState.value,
            state.receiveCurrencyState.value,
        )
        state.exchangeRateState.value = exchangeRates.from
        state.reverseExchangeRateState.value = exchangeRates.to
    }

    private fun onHideSuccess() {
        _stateFlow.update {
            it.copy(
                successState = it.successState.copy(
                    isShown = false,
                ),
            )
        }
    }

    private val state: SendPageState
        get() = _stateFlow.value
    private val _stateFlow = MutableStateFlow(
        SendPageState(
            currencyDialogState = CurrencyDialogState(
                onCurrencyPicked = ::onCurrencyPicked,
            ),
            successState = AnimationState(
                isShown = false,
                onFinish = ::onHideSuccess,
            ),
            onTransfer = ::onTransfer,
            onCurrencyChangeRequsted = ::onCurrencyChangeRequsted,
            onDocumentRequsted = ::onDocumentPicker,
        ),
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            prepareBalances()
            reloadCurrencies()
            updateCurrencies()
            updateCurrenciesInDialog()
            updateTransferPrices()
        }
    }
}

data class SendPageState(
    val currencyDialogState: CurrencyDialogState = CurrencyDialogState(
        onCurrencyPicked = {},
    ),
    val successState: AnimationState = AnimationState(),
    val sendAmountState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val sendCurrencyState: MutableState<Currency> = mutableStateOf(Currency.getInstance(Locale.getDefault())),
    val receiveAmountState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val receiveCurrencyState: MutableState<Currency> = mutableStateOf(Currency.getInstance(Locale.getDefault())),
    val balanceState: MutableState<String> = mutableStateOf(""),
    val availableBalanceState: MutableState<String> = mutableStateOf(""),
    val exchangeRateState: MutableState<String> = mutableStateOf(""),
    val reverseExchangeRateState: MutableState<String> = mutableStateOf(""),
    val isTransferRegularState: MutableState<Boolean> = mutableStateOf(false),
    val expressPriceAmount: MutableState<String> = mutableStateOf(""),
    val purposeTypeState: MutableState<PurposeType> = mutableStateOf(PurposeType.NONE),
    val purposeTextState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val dropdownShownState: MutableState<Boolean> = mutableStateOf(false),
    val isSaveBeneficiaryState: MutableState<Boolean> = mutableStateOf(false),
    val isAgreedState: MutableState<Boolean> = mutableStateOf(false),
    val onCurrencyChangeRequsted: (Boolean) -> Unit,
    val onDocumentRequsted: () -> Unit = {},
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
