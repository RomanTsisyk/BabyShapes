package com.roman_tsisyk.contrast_canvas.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.roman_tsisyk.contrast_canvas.R
import com.roman_tsisyk.contrast_canvas.viewmodel.RandomAnimalIconPagerViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private const val INITIAL_SCALE = 0.5f
private const val TARGET_SCALE = 1f
private const val INITIAL_ALPHA = 1f
private const val TARGET_ALPHA = 0.01f
private const val ANIMATION_DURATION_MS = 2500
private const val IMAGE_SIZE_DP = 500

@Composable
fun ImageDisplayWithNavigation(viewModel: RandomAnimalIconPagerViewModel) {
    val randomIconIndex = viewModel.randomIconIndex
    val context = LocalContext.current
    val iconNames = context.resources.getStringArray(R.array.icon_names)
    val icons = iconNames.map { name -> painterResource(id = getResourceId(name)) }

    val scale = remember { Animatable(INITIAL_SCALE) }
    val alpha = remember { Animatable(INITIAL_ALPHA) }

    LaunchedEffect(randomIconIndex.intValue) {
        scale.animateTo(
            targetValue = TARGET_SCALE, // Grow larger
            animationSpec = tween(durationMillis = ANIMATION_DURATION_MS)
        )
        scale.animateTo(
            targetValue = INITIAL_SCALE, // Shrink back
            animationSpec = tween(durationMillis = ANIMATION_DURATION_MS)
        )
        alpha.animateTo(
            targetValue = TARGET_ALPHA, // Fade in
            animationSpec = tween(durationMillis = ANIMATION_DURATION_MS)
        )
        viewModel.goToNextIcon(iconNames.size)
        alpha.snapTo(INITIAL_ALPHA)
        scale.snapTo(INITIAL_SCALE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(IMAGE_SIZE_DP.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                    alpha = alpha.value
                )
        ) {
            ImageDisplay(
                painter = icons[randomIconIndex.intValue],
                tint = viewModel.selectedColor.value,
                alpha = alpha.value,
                scale = scale.value
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        NavigationButtons(viewModel)
    }
}