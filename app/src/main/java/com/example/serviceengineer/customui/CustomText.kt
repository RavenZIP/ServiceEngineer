package com.example.serviceengineer.customui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.ui.theme.ThirdColor

@Composable
fun DottedText(text: String){
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }
    Text(
        text = text,
        onTextLayout = {
            layout = it
        },
        modifier = Modifier.drawBehind {
            layout?.let {
                val thickness = 4f
                val dashPath = 7f
                val spacingExtra = 4f
                val offsetY = 10f

                for (i in 0 until it.lineCount) {
                    drawPath(
                        path = Path().apply {
                            moveTo(it.getLineLeft(i), it.getLineBottom(i) - spacingExtra + offsetY)
                            lineTo(it.getLineRight(i), it.getLineBottom(i) - spacingExtra + offsetY)
                        },
                        ThirdColor,
                        style = Stroke(
                            width = thickness,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashPath, dashPath), 0f)
                        )
                    )
                }
            }
        }
            .padding(bottom = 20.dp),
    )
}