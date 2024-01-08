package com.roman_tsisyk.contrast_canvas

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    RandomAnimalIconPager()
}

@Composable
fun RandomAnimalIconPager() {
    val configuration = LocalConfiguration.current
    val isWide = isWideScreen(configuration)
    val context = LocalContext.current
    val iconNames = context.resources.getStringArray(R.array.icon_names)

    val colors: List<Color> = listOf(Color.Black, Color.Red, Color.Yellow, Color.Blue)

    val viewModel: RandomAnimalIconPagerViewModel = viewModel()
    val randomIconIndex = viewModel.randomIconIndex
    val isAlphaAnimationOn = viewModel.isAlphaAnimationOn
    val isScalingAnimationOn = viewModel.isScalingAnimationOn
    val selectedColor = viewModel.selectedColor
    val animationDuration = viewModel.animationDuration
    val isSettingsVisible = viewModel.isSettingsVisible.value
    val icons = remember { iconNames }.map { name -> painterResource(id = getResourceId(name)) }

    val alpha by animateFloatAsState(
        targetValue = if (!viewModel.isAlphaAnimationOn.value) 0f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = viewModel.animationDuration.intValue,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (!viewModel.isScalingAnimationOn.value) 0.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = viewModel.animationDuration.intValue),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(key1 = isAlphaAnimationOn.value, key2 = isScalingAnimationOn.value) {
        if (isAlphaAnimationOn.value && isScalingAnimationOn.value) {
            while (isActive) {
                delay(viewModel.animationDuration.intValue.toLong() * 2)
                viewModel.goToNextIcon(iconNames.size)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        val addSpacerOnTop = !isSettingsVisible && !isWide
        AdaptiveLayout(isWide) {
            ImageDisplay(
                icons,
                randomIconIndex,
                iconNames,
                scale,
                alpha,
                selectedColor.value,
                addSpacerOnTop
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.isSettingsVisible.value) {
                    SettingsCard(
                        isAlphaAnimationOn,
                        isScalingAnimationOn,
                        animationDuration,
                        isWide,
                        colors,
                        selectedColor,
                        viewModel
                    )


                    Card (modifier = Modifier.padding(8.dp)){
                        Column(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = stringResource(R.string.settings),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = isAlphaAnimationOn.value,
                                    onCheckedChange = {
                                        Log.d("Roman", "checked: $it")
                                        viewModel.changeSeetings(it)
                                    })
                                Text(
                                    text = stringResource(R.string.turn_on_animation),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.choose_image_color),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = if (isWide) Arrangement.SpaceAround else Arrangement.SpaceBetween
                                ) {
                                    colors.forEach { color ->
                                        CircleColorChoice(
                                            color = color, isSelected = color == selectedColor.value
                                        ) {
                                            viewModel.updateSelectedColor(color)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { viewModel.toggleSettingsVisibility() },
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(if (viewModel.isSettingsVisible.value) "Hide Settings" else "Show Settings")
                }
                NavigationButtons(viewModel, iconNames.size)
            }
        }
    }
}

@Composable
private fun SettingsCard(
    isAlphaAnimationOn: MutableState<Boolean>,
    isScalingAnimationOn: MutableState<Boolean>,
    animationDuration: MutableIntState,
    isLandscape: Boolean,
    colors: List<Color>,
    selectedColor: MutableState<Color>,
    viewModel: RandomAnimalIconPagerViewModel
) {

}

@Composable
fun isWideScreen(configuration: Configuration): Boolean {
    val isTablet = configuration.smallestScreenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    return isLandscape || isTablet
}

@Composable
private fun ImageDisplay(
    icons: List<Painter>,
    randomIconIndex: MutableIntState,
    iconNames: Array<String>,
    scale: Float,
    alpha: Float,
    selectedColor1: Color,
    addPaddingOnTop: Boolean
) {
    if (addPaddingOnTop) Spacer(modifier = Modifier.height(75.dp))

    Image(painter = icons[randomIconIndex.intValue],
        contentDescription = null,
        modifier = Modifier
            .padding(32.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, _ ->
                    randomIconIndex.intValue = Random.nextInt(0, iconNames.size)
                }
            }
            .scale(scale = scale)
            .graphicsLayer(alpha = alpha),
        colorFilter = ColorFilter.tint(selectedColor1) // This line applies the red tint.
    )
}

@Composable
private fun NavigationButtons(
    viewModel: RandomAnimalIconPagerViewModel, iconNamesArraySize: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Button(onClick = {
            viewModel.goToPreviousIcon(iconNamesArraySize)
        }) {
            Text(stringResource(R.string.previous))
        }

        Button(onClick = {
            viewModel.goToNextIcon(iconNamesArraySize)
        }) {
            Text(stringResource(R.string.next))
        }
    }
}


fun getResourceId(resourceName: String): Int {
    return R.drawable::class.java.getField(resourceName).getInt(null)
}


@Composable
fun CircleColorChoice(
    color: Color, isSelected: Boolean = false, onClick: () -> Unit
) {
    Box(modifier = Modifier
        .size(50.dp)
        .clip(CircleShape)  // Clip the Box to make it a circle
        .background(color)
        .let { if (isSelected) it.border(2.dp, Color.Gray, CircleShape) else it }
        .clickable(onClick = onClick))
}


@Composable
fun AdaptiveLayout(
    isHorizontalMode: Boolean, content: @Composable () -> Unit
) {
    if (isHorizontalMode) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}