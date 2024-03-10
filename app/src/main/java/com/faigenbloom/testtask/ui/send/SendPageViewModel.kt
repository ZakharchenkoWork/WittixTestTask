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
import com.faigenbloom.testtask.domain.transfer.CalculateTransferAmountUseCase
import com.faigenbloom.testtask.domain.transfer.GetTransferPriceUseCase
import com.faigenbloom.testtask.ui.common.animations.AnimationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

const val NO_AMOUNT_VALUE = "0"

class SendPageViewModel(
    private val getMainCurrencyUseCase: GetMainCurrencyUseCase,
    private val getReceiverCurrencyUseCase: GetReceiverCurrencyUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val getTransferPriceUseCase: GetTransferPriceUseCase,
    private val calculateTransferAmountUseCase: CalculateTransferAmountUseCase,
) : ViewModel() {
    private var isLastChangedAmountWasSend: Boolean = true
    var onTransfer: () -> Unit = {}
    var onDocumentPickerRequested: () -> Unit = {}

    fun onDocumentLoaded(uriList: List<Uri>) {
        state.isLoadingState.value = true
        viewModelScope.launch {
            delay(3000L)
            _stateFlow.update {
                it.copy(
                    pickedDocuments = uriList,
                    successState = it.successState.copy(
                        isShown = true,
                        onFinish = {},
                    ),
                )
            }
            state.isLoadingState.value = false
        }
    }

    private fun onDocumentPicker() {
        onDocumentPickerRequested()
    }

    private fun checkPurposeError(): Boolean {
        val purposeType = state.purposeTypeState.value
        val purposeText = state.purposeTextState.value.text
        return purposeType == PurposeType.NONE ||
            (
                purposeType == PurposeType.OTHER &&
                    purposeText.isBlank()
                )
    }

    private fun onTransferRequseted() {
        state.isLoadingState.value = true
        viewModelScope.launch {
            recalculateAmount(isLastChangedAmountWasSend)
            if (checkAndShowTransferErrors()) {
                _stateFlow.update {
                    it.copy(
                        successState = it.successState.copy(
                            isShown = true,
                            onFinish = {
                                onTransfer()
                            },
                        ),
                    )
                }
                state.isLoadingState.value = false
            }
        }
    }

    private fun checkAndShowTransferErrors(): Boolean {
        return if (state.sendAmountState.value.text.isNotBlank() && state.sendErrorState.value.not()) {
            if (checkPurposeError().not()) {
                if (state.isAgreedState.value) {
                    true
                } else {
                    state.isAgreedErrorState.value = true
                    false
                }
            } else {
                state.purposeErrorState.value = true
                false
            }
        } else {
            state.sendErrorState.value = true
            state.animateErrorState.value = true
            false
        }
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
            recalculateAmount(isLastChangedAmountWasSend)
            state.animateErrorState.value = state.sendErrorState.value
        }
    }

    private fun onAmountChanged(isForSend: Boolean) {
        isLastChangedAmountWasSend = isForSend
        viewModelScope.launch {
            recalculateAmount(isForSend)
        }
    }

    private fun onTransferOptionsChanged() {
        viewModelScope.launch {
            recalculateAmount(isLastChangedAmountWasSend)
            state.animateErrorState.value = state.sendErrorState.value
        }
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

    private suspend fun recalculateAmount(isForSend: Boolean) {
        val fieldToCalculate = if (isForSend) {
            state.receiveAmountState
        } else {
            state.sendAmountState
        }
        val changedField = if (isForSend) {
            state.sendAmountState
        } else {
            state.receiveAmountState
        }
        val enteredAmount = changedField.value.text
        val exchangeRate = if (isForSend) {
            state.exchangeRateState.value
        } else {
            state.reverseExchangeRateState.value
        }
        val transferPrice = if (state.isTransferRegularState.value.not()) {
            state.expressPriceAmount.value
        } else {
            NO_AMOUNT_VALUE
        }

        fieldToCalculate.value = TextFieldValue(
            if (enteredAmount.isNotBlank()) {
                val calculatedAmount = calculateTransferAmountUseCase(
                    isForSend = isForSend,
                    amount = enteredAmount,
                    rate = exchangeRate,
                    transferPrice = transferPrice,
                )
                if (calculatedAmount.contains("-").not()) {
                    state.sendErrorState.value = false
                    calculatedAmount
                } else {
                    state.sendErrorState.value = true
                    NO_AMOUNT_VALUE
                }
            } else {
                state.sendErrorState.value = false
                NO_AMOUNT_VALUE
            },
        )
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
            onTransfer = ::onTransferRequseted,
            onCurrencyChangeRequsted = ::onCurrencyChangeRequsted,
            onDocumentRequsted = ::onDocumentPicker,
            onAmountChanged = ::onAmountChanged,
            onTransferOptionsChanged = ::onTransferOptionsChanged,
        ),
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        state.isLoadingState.value = true
        viewModelScope.launch {
            prepareBalances()
            reloadCurrencies()
            updateCurrencies()
            updateCurrenciesInDialog()
            updateTransferPrices()
            state.isLoadingState.value = false
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
    val sendErrorState: MutableState<Boolean> = mutableStateOf(false),
    val animateErrorState: MutableState<Boolean> = mutableStateOf(false),
    val receiveAmountState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val receiveCurrencyState: MutableState<Currency> = mutableStateOf(
        Currency.getInstance(
            Locale.getDefault(),
        ),
    ),
    val balanceState: MutableState<String> = mutableStateOf(""),
    val availableBalanceState: MutableState<String> = mutableStateOf(""),
    val exchangeRateState: MutableState<String> = mutableStateOf(""),
    val isLoadingState: MutableState<Boolean> = mutableStateOf(false),
    val reverseExchangeRateState: MutableState<String> = mutableStateOf(""),
    val isTransferRegularState: MutableState<Boolean> = mutableStateOf(false),
    val expressPriceAmount: MutableState<String> = mutableStateOf(""),
    val purposeTypeState: MutableState<PurposeType> = mutableStateOf(PurposeType.NONE),
    val purposeTextState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val purposeErrorState: MutableState<Boolean> = mutableStateOf(false),
    val dropdownShownState: MutableState<Boolean> = mutableStateOf(false),
    val isSaveBeneficiaryState: MutableState<Boolean> = mutableStateOf(false),
    val isAgreedState: MutableState<Boolean> = mutableStateOf(false),
    val isAgreedErrorState: MutableState<Boolean> = mutableStateOf(false),
    val pickedDocuments: List<Uri> = emptyList(),
    val onCurrencyChangeRequsted: (isForSend: Boolean) -> Unit = {},
    val onAmountChanged: (isForSend: Boolean) -> Unit = {},
    val onTransferOptionsChanged: () -> Unit = {},
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
