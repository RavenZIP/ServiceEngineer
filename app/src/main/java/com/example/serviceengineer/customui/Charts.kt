package com.example.serviceengineer.customui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.ui.theme.FourthColor
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor

@Composable
fun DoughnutChart1(
    values: List<Float>,
    legend: List<String>,
    size: Int,
    fontSize: Int,
    size2: Dp,
    colors: List<Color> = listOf(
        ThirdColor.copy(0.65f),
        FourthColor
    ),
    thickness: Dp = size.dp / 9
) {
    if (values.isNotEmpty() && legend.isNotEmpty()) {
        // Sum of all the values
        val sumOfValues = values.sum()

        // Calculate each proportion
        val proportions = values.map {
            it * 100 / sumOfValues
        }

        // Convert each proportion to angle
        val sweepAngles = proportions.map {
            360 * it / 100
        }

        Canvas(
            modifier = Modifier.size(size = size.dp)
        ) {

            var startAngle = -90f

            for (i in values.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
                )
                startAngle += sweepAngles[i]
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column {
            for (i in values.indices) {
                DisplayLegend(color = colors[i], legend = legend[i] + " (" + values[i].toInt().toString() + ")", size2, fontSize, true)
            }
        }
    }
    else
        Text(text = "Нет данных для построения графика...", letterSpacing = 0.5.sp)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BarChart(
    values: List<Float>,
    legend: List<String>,
    size: Int,
    fontSize: Int,
    size2: Dp,
    colors: List<Color> = listOf(
        ThirdColor.copy(0.65f),
        ThirdColor.copy(0.25f),
    )
) {
    if (values.isNotEmpty() && legend.isNotEmpty()) {
        val positionList = mutableListOf<Float>()
        Log.d("l", legend.toString())
        Log.d("lc", legend.count().toString())
        Log.d("v", values.toString())
        values.forEach {
            if (positionList.isEmpty())
                positionList.add(70f)
            else
                positionList.add(positionList.last() + 170f)
        }
        Column() {
            Canvas(
                modifier = Modifier
                    .width(size.dp * 4)
                    .height(size.dp)
                    .clip(RoundedCornerShape(10))
                    .background(
                        ThirdColor.copy(0.1f)
                    )
            ) {
                for (i in values.indices) {
                    drawRoundRect(
                        color = colors[0],
                        topLeft = Offset(positionList[i], (size * 2.35).toFloat() - values[i] * 50),
                        size = Size(40f, values[i] * 50),
                        cornerRadius = CornerRadius(10f)
                    )
                }
            }
            //Spacer(modifier = Modifier.height(10.dp))

            Column(
                Modifier.padding(start = 5.dp),
            ) {
                for (i in values.indices) {
                    Spacer(modifier = Modifier.height(if (i == 0) 10.dp else 5.dp))
                    DisplayLegend(
                        color = if (legend[0] == "Выполнено" || legend[0] == "Завершено") colors[i] else colors[0],
                        legend = (i + 1).toString() + " - " + legend[i] + " (" + values[i].toInt() + ")",
                        size2,
                        fontSize,
                        false
                    )
                }
            }
        }
    } else
        Text(text = "Нет данных для построения графика...", letterSpacing = 0.5.sp)
}

@Composable
private fun DisplayLegend(color: Color, legend: String, size: Dp, fontSize: Int, rect: Boolean) {

    if (rect) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .background(color = color, shape = RoundedCornerShape(20))
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = legend,
                color = Color.Black,
                fontSize = fontSize.sp,
                letterSpacing = 0.sp
            )
        }
    } else {
        Text(
            text = legend,
            letterSpacing = 0.sp,
            color = Color.Black,
            fontSize = fontSize.sp
        )
    }
}