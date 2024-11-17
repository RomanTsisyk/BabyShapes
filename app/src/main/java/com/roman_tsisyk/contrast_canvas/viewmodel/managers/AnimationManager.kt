package com.roman_tsisyk.contrast_canvas.viewmodel.managers

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class AnimationManager {
    val isAlphaAnimationOn = mutableStateOf(true)
    val isScalingAnimationOn = mutableStateOf(true)
    val animationDuration = mutableIntStateOf(5000) // Default duration in milliseconds

    fun changeSettings(isOn: Boolean) {
        isAlphaAnimationOn.value = isOn
        isScalingAnimationOn.value = isOn
    }
}
