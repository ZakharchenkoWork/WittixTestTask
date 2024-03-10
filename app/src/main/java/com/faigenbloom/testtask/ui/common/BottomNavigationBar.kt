@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.placeholder.PlaceholderPageRoute
import com.faigenbloom.testtask.ui.send.SendPageRoute
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

data class BarItem(
    val destination: String?,
    val icon: Int,
    val label: Int,
)

val items = listOf(
    BarItem(
        destination = SendPageRoute(),
        icon = R.drawable.icon_dashboard,
        label = R.string.bar_dashboard,
    ),
    BarItem(
        destination = PlaceholderPageRoute(),
        icon = R.drawable.icon_statment,
        label = R.string.bar_statement,
    ),
    BarItem(
        destination = null,
        icon = R.drawable.icon_plus,
        label = R.string.bar_add,
    ),
    BarItem(
        destination = PlaceholderPageRoute(),
        icon = R.drawable.icon_debit_cards,
        label = R.string.bar_debit,
    ),
    BarItem(
        destination = PlaceholderPageRoute(),
        icon = R.drawable.icon_settings,
        label = R.string.bar_settings,
    ),
)

@Composable
fun WittixBottomNavigationBar(
    selectedIndex: Int,
    onDestinationChanged: (Int, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .topShadow(
                color = colorScheme.onPrimary.copy(alpha = 0.1f),
                offsetY = 2.dp,
                blurRadius = 16.dp,
            )
            .background(
                color = colorScheme.background,
                shape = CutOutShape,
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, barItem ->
            barItem.destination?.let { destination ->
                WittixBarItem(
                    modifier = Modifier.weight(0.2f),
                    barItem = barItem,
                    isSelected = selectedIndex == index,
                    onClick = {
                        onDestinationChanged(index, destination)
                    },
                )
            } ?: kotlin.run {
                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}

@Composable
private fun WittixBarItem(
    modifier: Modifier = Modifier,
    barItem: BarItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp),
            painter = painterResource(
                id = barItem.icon,
            ),
            contentDescription = null,
            tint = if (isSelected) {
                colorScheme.secondary
            } else {
                colorScheme.primary
            },
        )
        Text(
            modifier = Modifier,
            text = stringResource(id = barItem.label),
            color = if (isSelected) {
                colorScheme.secondary
            } else {
                colorScheme.primary
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun NavBarPreview() {
    TestTaskTheme {
        Scaffold(
            bottomBar = {
                WittixBottomNavigationBar(
                    selectedIndex = 0,
                    onDestinationChanged = { _, _ -> },
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                WittixFloatingActionButton(
                    onClicked = {},
                )
            },
        ) {
        }
    }
}
