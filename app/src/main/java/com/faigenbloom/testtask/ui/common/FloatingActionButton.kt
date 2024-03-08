package com.faigenbloom.testtask.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.WittixBottomNavigationBar
import com.faigenbloom.testtask.R
import com.faigenbloom.testtask.ui.theme.TestTaskTheme
import com.faigenbloom.testtask.ui.theme.fabGradientList

@Composable
fun WittixFloatingActionButton(
    onClicked: () -> Unit
) {
    val size = 60.dp
    val sizePx = with(LocalDensity.current) { size.toPx() }
    val gradientOffsetX = sizePx / 2f
    val gradientOffsetY = sizePx / 3f
    val gradientRadius = sizePx / 2f + gradientOffsetX / 3f
    Box(
        modifier = Modifier
            .size(size)
            .background(
                shape = CircleShape,
                brush = Brush.radialGradient(
                    colors = MaterialTheme.colorScheme.fabGradientList(),
                    center = Offset(gradientOffsetX, gradientOffsetY),
                    radius = gradientRadius,
                ),
            ).clickable {onClicked() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_plus),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.background,
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
                    onClicked = {}
                )
            },
        ) {
        }
    }
}
