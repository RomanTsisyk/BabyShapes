//package com.roman_tsisyk.contrast_canvas
//
//import androidx.lifecycle.ViewModel
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.graphics.Color
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlin.random.Random
//import javax.inject.Inject
//
//@HiltViewModel
//class RandomAnimalIconPagerViewModel @Inject constructor() : ViewModel() {
//
//    private val iconNames = arrayOf("icon1", "icon2", "icon3") // Replace with actual icon names
//    val randomIconIndex = mutableStateOf(Random.nextInt(iconNames.size))
//    var selectedColor = mutableStateOf(Color.Black)
//
//    fun updateRandomIconIndex() {
//        randomIconIndex.value = Random.nextInt(iconNames.size)
//    }
//
//    fun updateSelectedColor(newColor: Color) {
//        selectedColor.value = newColor
//    }
//}
