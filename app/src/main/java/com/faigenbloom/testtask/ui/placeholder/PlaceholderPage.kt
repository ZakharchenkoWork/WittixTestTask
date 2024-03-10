@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask.ui.placeholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.animations.ErrorAnimatedIcon
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@Composable
fun PlaceholderPage(
    modifier: Modifier = Modifier,
    onNameClicked: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            ErrorAnimatedIcon()
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.placeholder_title),
                color = colorScheme.onError,
                textAlign = TextAlign.Center,
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.placeholder_message_pre),
                color = colorScheme.tertiary,
                textAlign = TextAlign.Center,
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                text = stringResource(R.string.placeholder_message_first),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Justify,
                style = typography.bodyMedium,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp,
                    ),
                text = stringResource(R.string.placeholder_message_second),
                color = colorScheme.secondary,
                textAlign = TextAlign.Center,
                style = typography.titleLarge,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.placeholder_message_third),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Justify,
                style = typography.bodyLarge,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.placeholder_message_forth),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Justify,
                style = typography.bodyLarge,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 32.dp),
            ) {
                Image(
                    modifier = Modifier.size(56.dp),
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Photo",
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.placeholder_message_fifth),
                        color = colorScheme.onBackground,
                        textAlign = TextAlign.End,
                        style = typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNameClicked() },
                        text = stringResource(R.string.placeholder_developer),
                        color = colorScheme.secondary,
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.End,
                        style = typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceholderPagePreview() {
    TestTaskTheme {
        Surface {
            PlaceholderPage(
                onNameClicked = {},
            )
        }
    }
}
