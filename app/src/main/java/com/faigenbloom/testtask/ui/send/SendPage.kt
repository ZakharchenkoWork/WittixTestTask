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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.BaseTextField
import com.faigenbloom.testtask.ui.common.CurrencyPicker
import com.faigenbloom.testtask.ui.common.CustomCheckbox
import com.faigenbloom.testtask.ui.common.DualStyleText
import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import com.faigenbloom.testtask.ui.common.TopBar
import com.faigenbloom.testtask.ui.common.animations.AnimateTabs
import com.faigenbloom.testtask.ui.common.animations.AnimatedVisibility
import com.faigenbloom.testtask.ui.common.animations.Success
import com.faigenbloom.testtask.ui.common.getFlag
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
fun TransferButton(state: SendPageState) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
            style = typography.bodyLarge,
        )
    }
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
            style = typography.bodySmall,
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
            firstStyle = SpanStyle(
                color = colorScheme.primary,
                fontSize = typography.bodySmall.fontSize,
            ),
            secondPart = stringResource(
                R.string.send_funds_agreement_more,
            ),
            secondStyle = SpanStyle(
                color = colorScheme.secondaryContainer,
                fontSize = typography.bodySmall.fontSize,
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
private fun BalanceFields(state: SendPageState) {
    val balanceText by remember { state.balanceState }
    val availableBalanceText by remember { state.availableBalanceState }
    val currency by remember { state.sendCurrencyState }
    DualStyleText(
        modifier = Modifier
            .fillMaxWidth(),
        firstPart = stringResource(R.string.send_funds_balance),
        firstStyle = SpanStyle(
            color = colorScheme.onPrimary,
            fontSize = typography.bodySmall.fontSize,
        ),
        secondPart = stringResource(
            R.string.send_funds_money_with_currency,
            currency.symbol,
            balanceText,
        ),
        secondStyle = SpanStyle(
            color = colorScheme.onBackground,
            fontSize = typography.bodySmall.fontSize,
        ),
        textAlign = TextAlign.Center,
    )
    DualStyleText(
        modifier = Modifier
            .fillMaxWidth(),
        firstPart = stringResource(R.string.send_funds_available_balance),
        firstStyle = SpanStyle(
            color = colorScheme.onPrimary,
            fontSize = typography.bodySmall.fontSize,
        ),
        secondPart = stringResource(
            R.string.send_funds_money_with_currency,
            currency.symbol,
            availableBalanceText,
        ),
        secondStyle = SpanStyle(
            color = colorScheme.onBackground,
            fontSize = typography.bodySmall.fontSize,
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
        style = typography.bodySmall,
    )
    RatesText(
        firstCurrency = sendCurrency,
        secondCurrency = receiveCurrency,
        firstRate = exchangeRateState,
        secondRate = reverseExchangeRateState,
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
        style = typography.bodySmall,
    )
}

@Composable
private fun TransferOptions(state: SendPageState) {
    var isTransferRegular by remember { state.isTransferRegularState }
    val expressCostAmount by remember { state.expressCostAmount }
    val sendCurrency by remember { state.sendCurrencyState }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 12.dp,
            ),
        text = stringResource(id = R.string.send_funds_transfer_options_title),
        color = colorScheme.onBackground,
        style = typography.bodyMedium,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OptionButton(
            modifier = Modifier
                .weight(0.5f),
            text = stringResource(R.string.send_funds_transfer_options_regular),
            isChecked = isTransferRegular,
            onClick = { isTransferRegular = true },
        )
        OptionButton(
            modifier = Modifier
                .weight(0.5f),
            text = stringResource(
                R.string.send_funds_transfer_options_express,
                stringResource(
                    R.string.send_funds_money_with_currency,
                    sendCurrency.symbol,
                    expressCostAmount,
                ),
            ),
            isChecked = isTransferRegular.not(),
            onClick = { isTransferRegular = false },
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
        style = typography.bodySmall,
    )
}

@Composable
private fun Header(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = title,
        color = colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = typography.bodySmall,
    )
}

@Composable
private fun MainInfo(state: SendPageState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
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
        )
        BalanceFields(state = state)
        AmountInputField(
            title = stringResource(R.string.send_funds_field_title_reveive),
            amountState = state.receiveAmountState,
            currencyState = state.receiveCurrencyState,
            onCurrencyClicked = { state.onCurrencyChangeRequsted(false) },
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
private fun Documents(state: SendPageState) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 12.dp),
        text = stringResource(R.string.send_funds_document_title),
        color = colorScheme.onPrimary,
        style = typography.bodyMedium,
    )
    Column(
        modifier = Modifier
            .padding(all = 12.dp)
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
                .padding(top = 12.dp)
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
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            style = typography.bodySmall,
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            text = stringResource(R.string.send_funds_document_types),
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            style = typography.bodySmall,
        )
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 20.dp,
                start = 12.dp,
            ),
        text = stringResource(R.string.send_funds_document_hint),
        color = colorScheme.primary,
        style = typography.bodySmall,
    )
}

@Composable
private fun AmountInputField(
    title: String,
    amountState: MutableState<String>,
    currencyState: MutableState<Currency>,
    onCurrencyClicked: () -> Unit,
) {
    var sendAmountText by remember { amountState }
    val sendCurrency by remember { currencyState }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 12.dp),
        text = title,
        color = colorScheme.onPrimary,
        style = typography.bodyMedium,
    )
    Row(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = colorScheme.secondaryContainer,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BaseTextField(
            modifier = Modifier
                .weight(0.7f)
                .padding(all = 8.dp),
            value = sendAmountText,
            textStyle = typography.titleLarge.copy(
                color = colorScheme.onBackground,
            ),
            hint = stringResource(id = R.string.send_funds_amount),
            onValueChange = { sendAmountText = it },
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 4.dp),
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
                    .padding(start = 8.dp)
                    .size(20.dp),
                painter = painterResource(id = sendCurrency.getFlag()),
                contentDescription = "",
            )
            Text(
                modifier = Modifier,
                text = sendCurrency.currencyCode,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = typography.bodySmall,
            )
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.icon_dropdown),
                tint = colorScheme.tint(),
                contentDescription = "",
            )
        }
    }
}

@Composable
private fun PurposeFields(state: SendPageState) {
    var purposeType by remember { state.purposeTypeState }
    var purposeText by remember { state.purposeTextState }
    var dropdownShown by remember { state.dropdownShownState }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 12.dp)
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
                    .size(12.dp),
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
                    .padding(horizontal = 12.dp)
                    .padding(top = 4.dp)
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
                    hint = stringResource(id = R.string.send_funds_purpose_value_hint),
                    textStyle = typography.titleLarge,
                    color = colorScheme.onBackground,
                    hintColor = colorScheme.onPrimaryContainer,
                    hintStyle = typography.bodyMedium,
                    onValueChange = { purposeText = it },
                )
            }
        }
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
                    sendAmountState = remember { mutableStateOf("100,000") },
                    sendCurrencyState = remember { mutableStateOf(Currency.getInstance(EUR)) },
                    receiveAmountState = remember { mutableStateOf("358,521.13") },
                    receiveCurrencyState = remember { mutableStateOf(Currency.getInstance(ILS)) },
                    balanceState = remember { mutableStateOf("2,340.00") },
                    availableBalanceState = remember { mutableStateOf("1,700.50") },
                    exchangeRateState = remember { mutableStateOf("3.61206") },
                    reverseExchangeRateState = remember { mutableStateOf("0,28") },
                    isTransferRegularState = remember { mutableStateOf(false) },
                    expressCostAmount = remember { mutableStateOf("50") },
                    purposeTypeState = remember { mutableStateOf(PurposeType.NONE) },
                    purposeTextState = remember { mutableStateOf("") },
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
