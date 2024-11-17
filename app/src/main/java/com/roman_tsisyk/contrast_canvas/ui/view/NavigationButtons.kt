package com.roman_tsisyk.contrast_canvas.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman_tsisyk.contrast_canvas.viewmodel.RandomAnimalIconPagerViewModel

@Composable
fun NavigationButtons(viewModel: RandomAnimalIconPagerViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Button(onClick = { viewModel.goToPreviousIcon(10) }) {
            Text(text = "Previous")
        }
        Button(onClick = { viewModel.goToNextIcon(10) }) {
            Text(text = "Next")
        }
    }
}
