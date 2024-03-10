package com.faigenbloom.testtask.ui.send.compose

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.common.getName
import com.faigenbloom.testtask.ui.send.SendPageState
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@Composable
fun Documents(state: SendPageState) {
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
        if (state.pickedDocuments.isNotEmpty()) {
            DocumentsList(documentsList = state.pickedDocuments)
        } else {
            NoDocuments()
        }
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
private fun DocumentsList(documentsList: List<Uri>) {
    LazyRow(horizontalArrangement = Arrangement.Start) {
        items(documentsList.size) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = colorScheme.primaryContainer,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp
                        )
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
                        .padding(bottom = 16.dp),
                    text = documentsList[it].getName().ifBlank {
                        "File name"
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorScheme.onBackground,
                    style = typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun NoDocuments() {
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

@Preview(showBackground = true)
@Composable
private fun DocumentsEmptyPreview() {
    TestTaskTheme {
        Surface {
            Column {
                Documents(state = SendPageState())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentsListPreview() {
    TestTaskTheme {
        Surface {
            Column {
                Documents(
                    state = SendPageState(
                        pickedDocuments = listOf(
                            Uri.EMPTY
                        )
                    )
                )
            }
        }
    }
}
