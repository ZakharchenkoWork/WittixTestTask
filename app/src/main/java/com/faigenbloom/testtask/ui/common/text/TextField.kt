package com.faigenbloom.testtask.ui.common.text

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    hint: @Composable () -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle.copy(
            color = color,
        ),
        onValueChange = {
            if (visualTransformation is MoneyTextTransformation &&
                it.text.contains(InputFilterRegex.DecimalInput)
            ) {
                onValueChange(filteredDecimalText(it))
            } else {
                onValueChange(it)
            }
        },
        decorationBox = { innerTextField ->
            if (value.text.isBlank()) {
                hint()
            }
            innerTextField()
        },
    )
}
