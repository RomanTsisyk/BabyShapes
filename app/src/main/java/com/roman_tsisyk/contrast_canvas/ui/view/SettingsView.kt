package com.roman_tsisyk.contrast_canvas.ui.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.roman_tsisyk.contrast_canvas.viewmodel.RandomAnimalIconPagerViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsView(viewModel: RandomAnimalIconPagerViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Settings",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Alpha Animation Setting
        Text(
            text = "Enable Alpha Animation",
        )
        Text(
            text = "When enabled, the icons will fade in and out during the animation.",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                enabled = false,
                checked = viewModel.isAlphaAnimationOn.value,
                onCheckedChange = { viewModel.changeSettings(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Alpha Animation")
        }

        // Scaling Animation Setting
        Text(
            text = "Enable Scaling Animation",
        )
        Text(
            text = "When enabled, the icons will grow larger and shrink back during the animation.",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                enabled = false,
                checked = viewModel.isScalingAnimationOn.value,
                onCheckedChange = { viewModel.changeSettings(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Scaling Animation")
        }

        // Color Selection Setting
        Text(
            text = "Select Color",
        )
        Text(
            text = "Choose a color to tint the icons during the animation.",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val colors = listOf(Color.Black, Color.Red, Color.Blue, Color.Yellow)
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                        .background(color)
                        .clickable { viewModel.updateSelectedColor(color) }
                )
            }
        }

    }
}