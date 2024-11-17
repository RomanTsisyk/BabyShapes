package com.roman_tsisyk.contrast_canvas.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roman_tsisyk.contrast_canvas.R
import com.roman_tsisyk.contrast_canvas.viewmodel.RandomAnimalIconPagerViewModel

@Composable
fun RandomAnimalIconPager(viewModel: RandomAnimalIconPagerViewModel = viewModel()) {
    val tabs = listOf("Image Display", "Settings")
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        ScrollableTabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the selected tab content
        when (selectedTab) {
            0 -> ImageDisplayWithNavigation(viewModel) // Tab for Image Display
            1 -> SettingsView(viewModel)              // Tab for Settings
        }
    }
}


fun getResourceId(resourceName: String): Int {
    return R.drawable::class.java.getField(resourceName).getInt(null)
}
