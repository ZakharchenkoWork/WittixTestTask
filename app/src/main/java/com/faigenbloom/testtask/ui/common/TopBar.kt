package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R

@Composable
fun TopBar(title: String) {
    Box(
        modifier = Modifier
            .padding(
                vertical = 24.dp,
                horizontal = 20.dp,
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .height(16.dp),
                painter = painterResource(R.drawable.icon_back),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
