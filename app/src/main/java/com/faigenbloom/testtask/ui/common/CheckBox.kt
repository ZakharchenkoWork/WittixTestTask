package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.faigenbloom.testtask.R

@Composable
fun WittixCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
    color: Color,
) {
    val checkBoxState = remember { mutableStateOf(checked) }

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(4.dp),
                color = color,
            )
            .background(Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = colorScheme.tertiaryContainer
                ),
                onClick = {
                    onCheckedChange(checkBoxState.value.not())
                    checkBoxState.value = checkBoxState.value.not()
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (checkBoxState.value) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.icon_checkbox_checked),
                tint = color,
                contentDescription = "CheckBox",
            )
        }
    }
}
