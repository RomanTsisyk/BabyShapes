package com.roman_tsisyk.contrast_canvas

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class RandomAnimalIconPagerViewModel : ViewModel() {

    val randomIconIndex = mutableIntStateOf(Random.nextInt(0, 100))
    val isAlphaAnimationOn = mutableStateOf(true)
    val isScalingAnimationOn = mutableStateOf(true)
    var selectedColor = mutableStateOf(Color.Black)
    val animationDuration = mutableIntStateOf(5_000)
    val isSettingsVisible = mutableStateOf(false)


    fun changeSeetings(isOn: Boolean){
        isAlphaAnimationOn.value = isOn
        isScalingAnimationOn.value = isOn
        randomIconIndex.intValue = Random.nextInt(0, 100)
    }

    fun toggleSettingsVisibility() {
        isSettingsVisible.value = !isSettingsVisible.value
    }

    fun updateSelectedColor(color: Color) {
        selectedColor.value = color
    }

    fun goToNextIcon(iconNamesArraySize: Int) {
        randomIconIndex.value = (randomIconIndex.value + 1) % iconNamesArraySize
    }

    fun goToPreviousIcon(iconNamesArraySize: Int) {
        randomIconIndex.value =
            (randomIconIndex.value - 1 + iconNamesArraySize) % iconNamesArraySize
    }
}
