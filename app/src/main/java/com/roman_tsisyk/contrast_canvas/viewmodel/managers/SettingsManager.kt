package com.roman_tsisyk.contrast_canvas.viewmodel.managers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class SettingsManager {
    val isSettingsVisible = mutableStateOf(false)
    val selectedColor = mutableStateOf(Color.Black)

    fun toggleSettingsVisibility() {
        isSettingsVisible.value = !isSettingsVisible.value
    }

    fun updateSelectedColor(color: Color) {
        selectedColor.value = color
    }
}