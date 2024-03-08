package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chargemap.compose.numberpicker.ListItemPicker
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import java.util.Currency
import java.util.Locale

@Composable
fun CurrencyPicker(
    currencies: List<Currency>,
    chosenCurrency: Currency,
    onCurrencyPicked: (Currency) -> Unit,
) {
    var currencyState by rememberSaveable {
        mutableStateOf(chosenCurrency)
    }
    Dialog(
        onDismissRequest = { onCurrencyPicked(chosenCurrency) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = colorScheme.surface),
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                    ),
                text = stringResource(R.string.currency_dialog_title),
                color = colorScheme.onSurface,
                style = typography.bodyLarge,
            )
            ListItemPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                value = currencyState,
                onValueChange = { currencyState = it },
                list = currencies,
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.tertiaryContainer,
                ),
                onClick = { onCurrencyPicked(currencyState) },
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    text = stringResource(R.string.dialog_button_done),
                    color = colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.Medium,
                    style = typography.bodyLarge,
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrencyChooserPreview() {
    TestTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.surface,
        ) {
            CurrencyPicker(
                currencies = ArrayList(Currency.getAvailableCurrencies()),
                chosenCurrency = Currency.getInstance(Locale.getDefault()),
                onCurrencyPicked = { },
            )
        }
    }
}
