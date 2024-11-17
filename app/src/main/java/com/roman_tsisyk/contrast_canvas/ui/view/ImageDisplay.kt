package com.roman_tsisyk.contrast_canvas.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ImageDisplay(
    painter: Painter,
    alpha: Float,
    scale: Float,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
        colorFilter = ColorFilter.tint(tint)
    )
}
