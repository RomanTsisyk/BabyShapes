package com.roman_tsisyk.contrast_canvas.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman_tsisyk.contrast_canvas.viewmodel.RandomAnimalIconPagerViewModel

@Composable
fun SettingsCard(viewModel: RandomAnimalIconPagerViewModel) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = "Settings", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // Add other settings UI components here
        }
    }
}
