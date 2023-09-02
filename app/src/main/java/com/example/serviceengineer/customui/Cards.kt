package com.example.serviceengineer.customui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.example.serviceengineer.screens.helpers.requestList
import com.example.serviceengineer.screens.helpers.selectedNotes
import com.example.serviceengineer.ui.theme.*
import java.lang.Math.round
import kotlin.math.roundToInt

@Composable
fun Style1(
    title: String,
    description: String,
    icon: ImageVector,
    iconDescription: String,
    onClickAction: ()-> Unit,
    backgroundColor: Color,
    tint: Color
){
    TextButton(
        onClick = onClickAction,
        Modifier
            .fillMaxWidth(if (backgroundColor == SecondColor) 0.9f else 1f)
            .padding(top = if (backgroundColor == SecondColor) 10.dp else 0.dp),
        shape = RoundedCornerShape(if (backgroundColor == SecondColor) 10.dp else 0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = if (backgroundColor == SecondColor) 10.dp else 5.dp,
                    bottom = if (backgroundColor == SecondColor) 10.dp else 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                icon,
                modifier = Modifier.padding(start = if (backgroundColor == SecondColor) 10.dp else 20.dp),
                contentDescription = iconDescription,
                tint = tint
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = title,
                    fontSize = 19.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )

                if (description != "")
                    Text(
                        text = description,
                        fontSize = 13.sp,
                        color = TitleGrey,
                        fontWeight = FontWeight.W400,
                        letterSpacing = 0.5.sp
                    )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Style2(TitleText: String, Text:String, onClick: () -> Unit){
    TextButton(
        onClick = { onClick() },
        Modifier
            .fillMaxWidth(1f)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)) {
            Text(
                text = TitleText,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Text(
                text = Text,
                fontSize = 12.sp,
                color = TitleGrey,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChartWithTitle(
    TitleText: String,
    values: List<Float>,
    legend: List<String>,
    size: Int,
    fontSize: Int,
    size2: Dp,
    rect: Boolean,
    deviceType: String,
    onClick: () -> Unit
){
    TextButton(
        onClick = { onClick() },
        Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = TitleText,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "",
                    Modifier.size(25.dp)
                )
            }

            if (rect)
                Spacer(modifier = Modifier.height(25.dp))
            else {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(start = if (deviceType == "нет данных...") 0.dp else 5.dp),
                    text = "Устройство: ${deviceType.lowercase()}",
                    letterSpacing = 0.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Column(Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (rect)
                    DoughnutChart1(values, legend, size, fontSize, size2)
                else
                    Box(modifier = Modifier.fillMaxWidth().align(Alignment.Start)){
                        BarChart(values, legend, size, fontSize, size2)
                    }
            }
        }
    }
}

@Composable
fun AboutAppStyle(Image: Painter, TitleText: String, Text: String){
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = SecondColor,
        elevation = 0.dp
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp, top = 40.dp, end = 20.dp, start = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = Image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ThirdColor.copy(0.8f))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = TitleText,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = Text,
                fontSize = 16.sp,
                color = TitleGrey,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ServiceEngineer - это приложение для сервисных инженеров, которое ориентировано " +
                        "как на сотрудников сервисных центров, так и на самозанятых мастеров." +
                        "\n\nОно призвано обеспечить помощь при обслуживании устройства, обеспечить контроль " +
                        "складских ресурсов и оказываемых услуг, упростить составление документации при " +
                        "поступлении устройства в ремонт и при его выдаче.",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun Style5(TitleText: String, Text: String, Url: String){
    val uriHandler = LocalUriHandler.current
    TextButton(
        onClick = {
            uriHandler.openUri(Url)
        },
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp, start = 5.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = TitleText,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = Text,
                    fontSize = 13.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Style7(
    TitleText: String,
    Text: String,
    BorderRadius: Dp,
    onClick: () -> Unit,
    onLongPress: () -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .border(BorderRadius, ThirdColor, RoundedCornerShape(10.dp))
        .combinedClickable(
            onClick = { onClick() },
            onLongClick = { onLongPress() }
        ),
        backgroundColor = MainColor,
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
            Text(
                text = TitleText,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                letterSpacing = 0.5.sp
            )
            Text(
                text = Text,
                fontSize = 13.sp,
                color = TextGrey,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 10.dp, bottom = 5.dp),
                letterSpacing = 0.5.sp
            )
        }
    }
}

private data class DottedShape(
    val step: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepCount = (size.width / stepPx).roundToInt()
        val actualStep = size.width / stepCount
        val dotSize = Size(width = actualStep / 2, height = size.height)
        for (i in 0 until stepCount) {
            addRect(
                Rect(
                    offset = Offset(x = i * actualStep, y = 0f),
                    size = dotSize
                )
            )
        }
        close()
    })
}

@Composable
fun DotsFillMaxWidth(){
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(ThirdColor.copy(0.8f), shape = DottedShape(step = 5.dp))
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CategoryButton(Text: String, onClick: () -> Unit){
    TextButton(
            onClick = {
                onClick()
            },
        Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = Text,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ServiceCard(TitleText: String, Text: String, Price: String, borderColor: Color, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(5.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth(0.65f)) {
                Text(
                    text = TitleText,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = Text,
                    fontSize = 13.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 0.5.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$Price ₽",
                    fontSize = 14.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun SparePartCard(
    TitleText: String,
    Text: String,
    Price: String,
    Count: String,
    borderColor: Color,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(5.dp),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth(0.65f)) {
                Text(
                    text = TitleText,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = Text,
                    fontSize = 14.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 0.5.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "$Count шт.",
                    fontSize = 14.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 0.5.sp
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$Price ₽",
                    fontSize = 14.sp,
                    color = TitleGrey,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun UserJobCard(
    title: String,
    text: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable {
                onClick()
            },
        backgroundColor = MainColor,
        border = BorderStroke(2.dp, FourthColor),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = TitleGrey
            )
        }
    }
}

/*BasicTextField(
         text.value,
         onValueChange = { text.value = it },
         modifier = Modifier.fillMaxWidth(0.9f),
*//*             .indicatorLine(
                 enabled = enabled,
                 isError = false,
                 interactionSource = interactionSource,
                 colors = colors,
                 focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                 unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
             ),*//*
         textStyle = TextStyle(fontSize = 16.sp),
         interactionSource = interactionSource,
         singleLine = true,
    ){
         TextFieldDefaults.OutlinedTextFieldDecorationBox(
             text.value,
             innerTextField = it,
             leadingIcon = { Icon(imageVector = Icon, contentDescription = "email") },
             isError = isError.value,
             label = { Text(label, fontSize = 16.sp) },
             singleLine = true,
             visualTransformation = VisualTransformation.None,
             enabled = enabled,
             interactionSource = interactionSource
         )
     }*/