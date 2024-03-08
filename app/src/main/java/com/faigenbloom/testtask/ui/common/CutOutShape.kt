package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val CutOutShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val boundingRectangle = Path().apply {
            addRect(Rect(0f, 0f, size.width, size.height))
            close()
        }
        val path = Path().apply {
            val width = size.width
            val height = size.height

            moveTo(0f, 0f)
            lineTo(width / 4, 0f)

            cubicTo(
                x1 = width / 2.17f,
                y1 = 0f, // Start corner
                x2 = width / 2.53f,
                y2 = height / 2.34f,
                x3 = width / 2,
                y3 = height / 2f,
            )
            cubicTo(
                x1 = width - width / 2.53f,
                y1 = height / 2.34f,
                x2 = width - width / 2.17f,
                y2 = 0f,
                x3 = width - width / 4,
                y3 = 0f,
            )

            lineTo(width, 0f)
            // Subtract this path from the bounding rectangle
            op(boundingRectangle, this, PathOperation.Difference)
        }
        return Outline.Generic(path)
    }
}

@Preview
@Composable
fun CutOutShapePreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(CutOutShape)
                .background(Color.Yellow),
        )
    }
}
