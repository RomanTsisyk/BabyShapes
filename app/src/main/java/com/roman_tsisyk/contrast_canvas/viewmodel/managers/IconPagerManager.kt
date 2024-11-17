package com.roman_tsisyk.contrast_canvas.viewmodel.managers

import androidx.compose.runtime.mutableIntStateOf
import kotlin.random.Random

class IconPagerManager {
    val randomIconIndex = mutableIntStateOf(Random.nextInt(0, 100))

    fun goToNextIcon(iconCount: Int) {
        randomIconIndex.intValue = (randomIconIndex.intValue + 1) % iconCount
    }

    fun goToPreviousIcon(iconCount: Int) {
        randomIconIndex.intValue = (randomIconIndex.intValue - 1 + iconCount) % iconCount
    }
}
