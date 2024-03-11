package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.WittixCheckbox
import com.faigenbloom.testtask.ui.common.text.DualStyleText
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@Composable
fun SendingOptions(state: SendPageState) {
    var isSaveBeneficiaryState by remember { state.isSaveBeneficiaryState }
    var isAgreedState by remember { state.isAgreedState }
    var isAgreedError by remember { state.isAgreedErrorState }
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
        WittixCheckbox(
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
        WittixCheckbox(
            modifier = Modifier
                .size(16.dp)
                .testTag(AGREEMENT_CHECKBOX),
            checked = isAgreedState,
            onCheckedChange = {
                isAgreedState = it
                isAgreedError = isAgreedError && it.not()
            },
            color = if (isAgreedError.not()) {
                colorScheme.tertiaryContainer
            } else {
                colorScheme.error
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SendingOptionsPreview() {
    TestTaskTheme {
        Surface {
            SendingOptions(
                state = SendPageState(
                    isSaveBeneficiaryState = remember { mutableStateOf(true) },
                    isAgreedState = remember { mutableStateOf(false) },
                    isAgreedErrorState = remember { mutableStateOf(true) },
                )
            )
        }
    }
}
