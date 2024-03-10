@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask.ui.send

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.CurrencyPicker
import com.faigenbloom.testtask.ui.common.CustomCheckbox
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.TopBar
import com.faigenbloom.testtask.ui.common.animations.AnimateTabs
import com.faigenbloom.testtask.ui.common.animations.AnimatedVisibility
import com.faigenbloom.testtask.ui.common.animations.Success
import com.faigenbloom.testtask.ui.common.getFlag
import com.faigenbloom.testtask.ui.common.text.BaseTextField
import com.faigenbloom.testtask.ui.common.text.DualStyleText
import com.faigenbloom.testtask.ui.common.text.MoneyTextTransformation
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import com.faigenbloom.testtask.ui.theme.tint
import java.util.Currency
import java.util.Locale

@Composable
fun SendPage(
    modifier: Modifier = Modifier,
    state: SendPageState,
) {
    LazyColumn(modifier = modifier) {
        item {
            TopBar(stringResource(R.string.send_funds_title))
            Header(stringResource(R.string.send_funds_header))
            MainInfo(state = state)
            SendingOptions(state = state)
            TransferButton(state = state)
        }
    }
    CurrencyDialog(state = state.currencyDialogState)
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
private fun MainInfo(state: SendPageState) {
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

@Composable
private fun AmountInputField(
    title: String,
    amountState: MutableState<TextFieldValue>,
    currencyState: MutableState<Currency>,
    onCurrencyClicked: () -> Unit,
    onAmountChanged: () -> Unit,
) {
    var sendAmountText by remember { amountState }
    val sendCurrency by remember { currencyState }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp, start = 13.dp),
        text = title,
        color = colorScheme.onPrimary,
        style = typography.bodyMedium,
    )
    Row(
        modifier = Modifier
            .padding(all = 13.dp)
            .fillMaxWidth()
            .height(64.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = colorScheme.secondaryContainer,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BaseTextField(
            modifier = Modifier
                .weight(0.6f)
                .padding(all = 8.dp),
            value = sendAmountText,
            textStyle = typography.titleLarge,
            color = colorScheme.onBackground,
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
                    color = colorScheme.onPrimaryContainer,
                    style = typography.bodyMedium,
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 7.dp),
            color = colorScheme.primaryContainer,
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
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = typography.bodyMedium,
            )
            Icon(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.icon_dropdown),
                tint = colorScheme.tint(),
                contentDescription = "",
            )
        }
    }
}

@Composable
private fun BalanceFields(state: SendPageState) {
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

@Composable
private fun ReceiveFields(state: SendPageState) {
    val sendCurrency by remember { state.sendCurrencyState }
    val receiveCurrency by remember { state.receiveCurrencyState }
    val exchangeRateState by remember { state.exchangeRateState }
    val reverseExchangeRateState by remember { state.reverseExchangeRateState }
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(R.string.send_funds_exchange_label),
        color = colorScheme.onPrimary,
        textAlign = TextAlign.Center,
        style = typography.labelLarge,
    )
    RatesText(
        firstCurrency = sendCurrency,
        secondCurrency = receiveCurrency,
        firstRate = MoneyTextTransformation().format(exchangeRateState),
        secondRate = MoneyTextTransformation().format(reverseExchangeRateState),
    )
}

@Composable
private fun ExchangeRatesHint() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 20.dp,
                horizontal = 8.dp,
            ),
        text = stringResource(R.string.send_funds_exchange_rates_hint),
        color = colorScheme.onPrimary,
        textAlign = TextAlign.Justify,
        style = typography.labelLarge,
    )
}

@Composable
private fun TransferOptions(state: SendPageState) {
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
fun OptionButton(
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
        color = colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = typography.labelLarge,
    )
}

@Composable
private fun PurposeFields(state: SendPageState) {
    var purposeType by remember { state.purposeTypeState }
    var purposeText by remember { state.purposeTextState }
    var dropdownShown by remember { state.dropdownShownState }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 13.dp)
            .padding(top = 24.dp)
            .fillMaxWidth(),
        expanded = dropdownShown,
        onExpandedChange = {
            dropdownShown = it
        },
    ) {
        Row(
            modifier = Modifier
                .menuAnchor()
                .height(50.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = colorScheme.primaryContainer,
                )
                .clickable {
                    dropdownShown = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .padding(all = 8.dp),
                text = purposeType.stringResource().ifBlank {
                    stringResource(id = R.string.send_funds_purpose_hint)
                },
                color = if (purposeType == PurposeType.NONE) {
                    colorScheme.onPrimaryContainer
                } else {
                    colorScheme.onBackground
                },
                textAlign = TextAlign.Center,
                style = typography.bodyMedium,
            )
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.icon_dropdown),
                tint = colorScheme.tint(),
                contentDescription = "",
            )
        }

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = dropdownShown,
            onDismissRequest = { dropdownShown = false },
        ) {
            PurposeType.values().forEach { menuItemType ->
                if (menuItemType != PurposeType.NONE) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier
                                    .padding(all = 8.dp),
                                text = menuItemType.stringResource(),
                                color = if (purposeType == menuItemType) {
                                    colorScheme.secondaryContainer
                                } else {
                                    colorScheme.onBackground
                                },
                                style = typography.bodyMedium,
                            )
                        },
                        onClick = {
                            purposeType = menuItemType
                            dropdownShown = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        isVisible = purposeType == PurposeType.NONE || purposeType == PurposeType.OTHER,
    ) { isVisible ->
        if (isVisible) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 13.dp)
                    .padding(top = 7.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = colorScheme.primaryContainer,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BaseTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    value = purposeText,
                    textStyle = typography.titleLarge,
                    color = colorScheme.onBackground,
                    onValueChange = { purposeText = it },
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.send_funds_purpose_value_hint),
                            color = colorScheme.onPrimaryContainer,
                            style = typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Documents(state: SendPageState) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 13.dp),
        text = stringResource(R.string.send_funds_document_title),
        color = colorScheme.onPrimary,
        style = typography.bodyMedium,
    )
    Column(
        modifier = Modifier
            .padding(all = 13.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = colorScheme.tertiaryContainer,
            )
            .clickable { state.onDocumentRequsted() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 13.dp)
                .background(
                    color = colorScheme.tertiary,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.icon_document),
                tint = colorScheme.tertiaryContainer,
                contentDescription = "",
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = stringResource(R.string.send_funds_document_upload),
            color = colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = typography.bodySmall,
        )
        Text(
            modifier = Modifier.padding(top = 13.dp, bottom = 13.dp),
            text = stringResource(R.string.send_funds_document_types),
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            style = typography.labelLarge,
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 20.dp,
                start = 13.dp,
            ),
        text = stringResource(R.string.send_funds_document_hint),
        color = colorScheme.primary,
        style = typography.labelLarge,
    )
}

@Composable
fun SendingOptions(state: SendPageState) {
    var isSaveBeneficiaryState by remember { state.isSaveBeneficiaryState }
    var isAgreedState by remember { state.isAgreedState }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(end = 16.dp),
            text = stringResource(R.string.send_funds_beneficiary),
            color = colorScheme.primary,
            style = typography.labelLarge,
        )
        CustomCheckbox(
            modifier = Modifier.size(16.dp),
            checked = isSaveBeneficiaryState,
            onCheckedChange = { isSaveBeneficiaryState = it },
            color = colorScheme.tertiaryContainer,
        )
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        color = colorScheme.primaryContainer,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DualStyleText(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(end = 16.dp),
            firstPart = stringResource(R.string.send_funds_agreement_text),
            firstStyle = typography.labelLarge.toSpanStyle().copy(
                color = colorScheme.primary,
            ),
            secondPart = stringResource(
                R.string.send_funds_agreement_more,
            ),
            secondStyle = typography.labelLarge.toSpanStyle().copy(
                color = colorScheme.secondaryContainer,
                textDecoration = TextDecoration.Underline,
            ),
            textAlign = TextAlign.Justify,
        )
        CustomCheckbox(
            modifier = Modifier
                .size(16.dp),
            checked = isAgreedState,
            onCheckedChange = { isAgreedState = it },
            color = colorScheme.tertiaryContainer,
        )
    }
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

@Composable
private fun PurposeType.stringResource(): String {
    return when (this) {
        PurposeType.NONE -> ""
        PurposeType.ADVERTISING -> stringResource(R.string.send_funds_purpose_advertising)
        PurposeType.EXPENSE -> stringResource(R.string.send_funds_purpose_expense)
        PurposeType.FINANCIAL -> stringResource(R.string.send_funds_purpose_financial)
        PurposeType.LEGAL -> stringResource(R.string.send_funds_purpose_legal)
        PurposeType.MEDICAL -> stringResource(R.string.send_funds_purpose_medical)
        PurposeType.PAYROLL -> stringResource(R.string.send_funds_purpose_payroll)
        PurposeType.PROCESSING -> stringResource(R.string.send_funds_purpose_processing)
        PurposeType.TRADE -> stringResource(R.string.send_funds_purpose_trade)
        PurposeType.TRANSACTION -> stringResource(R.string.send_funds_purpose_transaction)
        PurposeType.TRANSPORTATION -> stringResource(R.string.send_funds_purpose_transportation)
        PurposeType.TRAVEL -> stringResource(R.string.send_funds_purpose_travel)
        PurposeType.PERSONAL -> stringResource(R.string.send_funds_purpose_personal)
        PurposeType.OTHER -> stringResource(R.string.send_funds_purpose_other)
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
