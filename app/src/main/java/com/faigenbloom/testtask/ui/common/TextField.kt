package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    hintStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    hintColor: Color = Color.Unspecified,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        textStyle = textStyle.copy(
            color = color,
        ),
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            if (value.isBlank()) {
                Text(
                    modifier = Modifier,
                    text = hint,
                    color = hintColor,
                    style = hintStyle,
                )
            }

            innerTextField()
        },
    )
}
