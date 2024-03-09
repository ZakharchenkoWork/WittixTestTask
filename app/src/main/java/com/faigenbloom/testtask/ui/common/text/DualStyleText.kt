package com.faigenbloom.testtask.ui.common.text

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun DualStyleText(
    modifier: Modifier = Modifier,
    firstPart: String,
    firstStyle: SpanStyle,
    secondPart: String,
    secondStyle: SpanStyle,
    textAlign: TextAlign,
    onClick: (() -> Unit)? = null,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = firstStyle) {
            append(firstPart)
        }

        pushStringAnnotation(tag = "", annotation = "")
        withStyle(style = secondStyle) {
            append(secondPart)
        }
        pop()
    }
    onClick?.let { onClickText ->
        ClickableText(
            modifier = modifier,
            text = annotatedString,
            style = TextStyle(textAlign = textAlign),
            onClick = { onClickText() },
        )
    } ?: run {
        Text(
            modifier = modifier,
            textAlign = textAlign,
            text = annotatedString,
        )
    }
}
