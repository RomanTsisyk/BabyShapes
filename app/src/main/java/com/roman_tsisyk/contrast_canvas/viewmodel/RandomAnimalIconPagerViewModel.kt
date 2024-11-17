package com.roman_tsisyk.contrast_canvas.viewmodel

import androidx.lifecycle.ViewModel
import com.roman_tsisyk.contrast_canvas.viewmodel.managers.AnimationManager
import com.roman_tsisyk.contrast_canvas.viewmodel.managers.IconPagerManager
import com.roman_tsisyk.contrast_canvas.viewmodel.managers.SettingsManager

class RandomAnimalIconPagerViewModel(
    private val animationManager: AnimationManager = AnimationManager(),
    private val iconPagerManager: IconPagerManager = IconPagerManager(),
    private val settingsManager: SettingsManager = SettingsManager()
) : ViewModel() {

    val randomIconIndex = iconPagerManager.randomIconIndex
    val isAlphaAnimationOn = animationManager.isAlphaAnimationOn
    val isScalingAnimationOn = animationManager.isScalingAnimationOn
    val selectedColor = settingsManager.selectedColor
    val animationDuration = animationManager.animationDuration
    val isSettingsVisible = settingsManager.isSettingsVisible

    fun toggleSettingsVisibility() {
        settingsManager.toggleSettingsVisibility()
    }

    fun updateSelectedColor(color: androidx.compose.ui.graphics.Color) {
        settingsManager.updateSelectedColor(color)
    }

    fun changeSettings(isOn: Boolean) {
        animationManager.changeSettings(isOn)
    }

    fun goToNextIcon(iconCount: Int) {
        iconPagerManager.goToNextIcon(iconCount)
    }

    fun goToPreviousIcon(iconCount: Int) {
        iconPagerManager.goToPreviousIcon(iconCount)
    }
}
