package com.faigenbloom.testtask.ui.send.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@Composable
fun ExchangeRatesHint() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 20.dp,
                horizontal = 8.dp,
            ),
        text = stringResource(R.string.send_funds_exchange_rates_hint),
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Preview(showBackground = true)
@Composable
private fun AmountInputFieldReceivePreview() {
    TestTaskTheme {
        Surface {
            Column {
                ExchangeRatesHint()
            }
        }
    }
}