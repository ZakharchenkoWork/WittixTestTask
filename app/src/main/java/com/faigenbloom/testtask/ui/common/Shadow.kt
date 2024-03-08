package com.faigenbloom.testtask.ui.common

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur.NORMAL
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val CORNERS_OFFSET: Float = -20f
fun Modifier.topShadow(
    color: Color = Color.Black,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), NORMAL))
            }
            frameworkPaint.color = color.toArgb()
            val topPixel = -offsetY.toPx()

            canvas.drawRect(
                left = CORNERS_OFFSET,
                top = topPixel,
                right = size.width + CORNERS_OFFSET,
                bottom = size.height,
                paint = paint,
            )
        }
    },
)
