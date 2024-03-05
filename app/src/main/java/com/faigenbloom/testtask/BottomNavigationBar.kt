@file:OptIn(ExperimentalMaterial3Api::class)

package com.faigenbloom.testtask

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.ui.send.SendPageRoute
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

data class BarItem(
    val destination: String,
    val icon: Int,
)

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onDestinationChanged: (Int, String) -> Unit,
) {
    val items = listOf(
        BarItem(
            destination = SendPageRoute(),
            icon = R.drawable.ic_launcher_background,
        ),
        BarItem(
            destination = SendPageRoute(),
            icon = R.drawable.ic_launcher_background,
        ),
        BarItem(
            destination = SendPageRoute(),
            icon = R.drawable.ic_launcher_background,
        ),
        BarItem(
            destination = SendPageRoute(),
            icon = R.drawable.ic_launcher_background,
        ),
        BarItem(
            destination = SendPageRoute(),
            icon = R.drawable.ic_launcher_background,
        ),
    )

    NavigationBar(
        modifier = Modifier.height(40.dp),
    ) {
        items.forEachIndexed { index, barItem ->
            NavigationBarItem(
                modifier = Modifier
                    .padding(4.dp)
                    .semantics { contentDescription = barItem.destination },
                selected = selectedIndex == index,
                onClick = {
                    onDestinationChanged(index, barItem.destination)
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        painter = painterResource(
                            id = barItem.icon,
                        ),
                        contentDescription = null,
                        tint = if (selectedIndex == index) {
                            colorScheme.primary
                        } else {
                            colorScheme.secondary
                        },
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun NavBarPreview() {
    TestTaskTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedIndex = 0,
                    onDestinationChanged = { _, _ -> },
                )
            },
        ) {
        }
    }
}
