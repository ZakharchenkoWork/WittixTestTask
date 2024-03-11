@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask.ui.send.compose

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.animations.AnimatedVisibility
import com.faigenbloom.testtask.ui.common.containerColor
import com.faigenbloom.testtask.ui.common.text.BaseTextField
import com.faigenbloom.testtask.ui.send.PurposeType
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import com.faigenbloom.testtask.ui.theme.tint

@Composable
fun PurposeFields(state: SendPageState) {
    val purposeType by remember { state.purposeTypeState }
    var purposeText by remember { state.purposeTextState }
    var dropdownShown by remember { state.dropdownShownState }
    var isError by remember { state.purposeErrorState }
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
                    color = containerColor(
                        isError = isError,
                        isChecked = purposeType != PurposeType.NONE,
                    ),
                )
                .clickable {
                    dropdownShown = true
                }
                .semantics {
                    contentDescription = containerDescriptionFor(PURPOSE_TYPE)
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
                style = if (purposeType == PurposeType.NONE) {
                    typography.bodyMedium
                } else {
                    typography.titleLarge
                },
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
        PurposeTypeDropDownMenu(
            state.dropdownShownState,
            state.purposeTypeState,
            state.purposeErrorState,
        )
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
                        color = containerColor(
                            isError = isError,
                            isChecked = purposeText.text
                                .isBlank()
                                .not(),
                        ),
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
                    onValueChange = {
                        purposeText = it
                        isError = false
                    },
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
private fun ExposedDropdownMenuBoxScope.PurposeTypeDropDownMenu(
    dropdownShownState: MutableState<Boolean>,
    purposeTypeState: MutableState<PurposeType>,
    purposeErrorState: MutableState<Boolean>,
) {
    var dropdownShown by remember { dropdownShownState }
    var purposeType by remember { purposeTypeState }
    var isError by remember { purposeErrorState }
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
                        isError = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun PurposeType.stringResource(): String {
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
private fun PurposeFieldsPickedPreview() {
    TestTaskTheme {
        Surface {
            Column {
                PurposeFields(
                    state = SendPageState(
                        purposeTypeState = remember { mutableStateOf(PurposeType.EXPENSE) },
                        dropdownShownState = remember { mutableStateOf(false) },
                        purposeErrorState = remember { mutableStateOf(false) }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PurposeFieldsOtherPreview() {
    TestTaskTheme {
        Surface {
            Column {
                PurposeFields(
                    state = SendPageState(
                        purposeTypeState = remember { mutableStateOf(PurposeType.OTHER) },
                        purposeTextState = remember { mutableStateOf(TextFieldValue("Random purpose")) },
                        dropdownShownState = remember { mutableStateOf(false) },
                        purposeErrorState = remember { mutableStateOf(false) }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PurposeFieldsErrorsPreview() {
    TestTaskTheme {
        Surface {
            Column {
                PurposeFields(
                    state = SendPageState(
                        purposeTypeState = remember { mutableStateOf(PurposeType.NONE) },
                        purposeTextState = remember { mutableStateOf(TextFieldValue()) },
                        dropdownShownState = remember { mutableStateOf(true) },
                        purposeErrorState = remember { mutableStateOf(true) }
                    )
                )
            }
        }
    }
}