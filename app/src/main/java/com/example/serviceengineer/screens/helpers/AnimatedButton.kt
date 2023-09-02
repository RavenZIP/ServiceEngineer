package com.example.serviceengineer.screens.helpers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.ui.theme.*

enum class BoxPosition { Top, Bottom }

fun getNextPosition(position: BoxPosition) =
    when (position) {
        BoxPosition.Top -> BoxPosition.Bottom
        BoxPosition.Bottom -> BoxPosition.Top
    }

@Composable
fun MultiButton(
    icon: ImageVector,
    left: Boolean,
    onClick: () -> Unit
){
    Box(contentAlignment = if (left) Alignment.BottomStart else Alignment.BottomEnd, modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(end = if (left) 0.dp else 20.dp, bottom = 20.dp, start = if (left) 20.dp else 0.dp,)
                .clip(RoundedCornerShape(16.dp)),
            onClick = {
                onClick()
            },
            shape = RectangleShape,
            backgroundColor = FourthColor
        ) {
            Icon(
                icon,
                contentDescription = "MultiButton",
                tint = activeText.copy(0.9f),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun MenuMultiButton(
    icon: ImageVector,
    padding: Dp,
    onClick: () -> Unit
){
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(end = 20.dp, bottom = padding)
                .clip(RoundedCornerShape(16.dp)),
            onClick = {
                onClick()
            },
            shape = RectangleShape,
            backgroundColor = FourthColor
        ) {
            Icon(
                icon,
                contentDescription = "MenuMultiButton",
                tint = activeText.copy(0.9f),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}