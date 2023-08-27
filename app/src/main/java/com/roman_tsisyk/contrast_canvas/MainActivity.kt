package com.roman_tsisyk.contrast_canvas

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomAnimalIconPager()
        }
    }
}

@Composable
fun RandomAnimalIconPager() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isTablet = configuration.smallestScreenWidthDp >= 600
    val orientation = configuration.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE

    val iconNames = context.resources.getStringArray(R.array.icon_names)
    val iconNamesRemembered = remember { iconNames }
    val icons = iconNamesRemembered.map { name -> painterResource(id = getResourceId(name)) }
    val randomIconIndex = remember { mutableIntStateOf(Random.nextInt(0, iconNames.size)) }
    val fadeInAnimation = rememberInfiniteTransition(label = "")
    val isAlfaAnimationOn = remember { mutableStateOf(true) }
    val isScalingActionOn = remember { mutableStateOf(true) }
    val colors: List<Color> = listOf(Color.Black, Color.Red, Color.Yellow, Color.Blue)
    var selectedColor by remember { mutableStateOf(colors.first()) }
    val animationDuration = remember { mutableIntStateOf(5_000) }
    val alpha by fadeInAnimation.animateFloat(
        initialValue = if (isAlfaAnimationOn.value) 0.25f else 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration.intValue, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val scale by fadeInAnimation.animateFloat(
        initialValue = if (isScalingActionOn.value) 0.25f else 1f, // Start very small
        targetValue = 1f, // End at full size
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration.intValue), repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(key1 = isAlfaAnimationOn.value, key2 = isScalingActionOn.value) {
        while (isAlfaAnimationOn.value && isScalingActionOn.value) {
            delay(animationDuration.intValue.toLong() * 2)
            randomIconIndex.intValue = Random.nextInt(0, iconNames.size)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        AdaptiveLayout(isLandscape) {

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
                colorFilter = ColorFilter.tint(selectedColor) // This line applies the red tint.
            )


            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = stringResource(R.string.settings), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                AdaptiveLayout(!isLandscape) {
                            Checkbox(checked = isAlfaAnimationOn.value && isScalingActionOn.value,
                                onCheckedChange = {
                                    isAlfaAnimationOn.value = it
                                    isScalingActionOn.value = it
                                    if (it) animationDuration.intValue = 5_000 else 0
                                })
                            Text(
                                text = stringResource(R.string.turn_on_animation),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(16.dp)
//                            .height(16.dp)
                        )
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
                                horizontalArrangement = if (!isTablet || !isLandscape) Arrangement.SpaceAround else Arrangement.SpaceBetween
                            ) {
                                colors.forEach { color ->
                                    CircleColorChoice(
                                        color = color, isSelected = color == selectedColor
                                    ) {
                                        selectedColor = color
                                    }
                                }
                            }
                        }
                    }
                }
                if (isLandscape) NavigationButtons(randomIconIndex, iconNames)
            }

            if (!isLandscape) NavigationButtons(randomIconIndex, iconNames)

        }

    }
}

@Composable
private fun NavigationButtons(
    randomIconIndex: MutableIntState, iconNames: Array<String>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Button(onClick = {
            randomIconIndex.intValue =
                (randomIconIndex.intValue - 1 + iconNames.size) % iconNames.size
        }) {
            Text(stringResource(R.string.previous))
        }

        Text(text = "${randomIconIndex.intValue + 1} of ${iconNames.size}")

        Button(onClick = {
            randomIconIndex.intValue = (randomIconIndex.intValue + 1) % iconNames.size
        }) {
            Text(stringResource(R.string.next))
        }
    }
}


fun getResourceId(resourceName: String): Int {
    return R.drawable::class.java.getField(resourceName).getInt(null)
}

@Preview(showBackground = true)
@Composable
fun RandomAnimalIconPagerPreview() {
    MaterialTheme {
        Surface {
            RandomAnimalIconPager()
        }
    }
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

/*
*   FuckRussians
    Dupa_putina
    jdjlkfbjdlk72
* */