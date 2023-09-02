package com.example.serviceengineer.screens.work

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.keys
import com.example.serviceengineer.screens.helpers.progressValue
import com.example.serviceengineer.ui.theme.*

@Composable
fun ProgressBar(){
    Column(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (TitleAppBar.value == "Схемы") "Прогресс загрузки" else "Прогресс заполнения",
                fontWeight = FontWeight.W500,
                color = ThirdColor
            )
            Text(
                text = (progressValue.value * 100).toInt().toString() + "%",
                fontWeight = FontWeight.W500,
                color = ThirdColor
            )
        }
        LinearProgressIndicator(
            progress = progressValue.value,
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth(),
            color = ThirdColor
        )
    }
}

@Composable
fun Option(
    value: MutableState<String>,
    text: String,
    weight: Float,
    id: Int,
    enabled: Boolean,
    onClick: () -> Unit
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        OutlinedTextField(
            value = value.value,
            onValueChange = {
                newText ->
                value.value = newText
                if (keys[id] == 0 && value.value.isNotEmpty()) {
                    keys[id] = 1
                    progressValue.value += weight
                }
                else if (value.value.isEmpty()){
                    keys[id] = 0
                    progressValue.value -= weight
                }
            },
            label = { Text(text) },
            modifier = Modifier.fillMaxWidth(0.75f).weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = ThirdColor,
                focusedBorderColor = ThirdColor,
                focusedLabelColor = ThirdColor,
                backgroundColor = SecondColor,
                unfocusedBorderColor = FourthColor,
                unfocusedLabelColor = ThirdColor.copy(0.8f),
                textColor = Color.Black,
                disabledBorderColor = FourthColor,
                disabledTextColor = activeText,
                disabledLabelColor = ThirdColor.copy(0.8f),
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            enabled = enabled
        )
        Box(modifier = Modifier
            .padding(top = 7.dp, start = 10.dp)
            .size(57.dp)
            .clip(RoundedCornerShape(15))
            .clickable(onClick = { onClick() })
            .background(SecondColor),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Open",
                tint = ThirdColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

fun checkKeys(index: Int, weight: Float){
    if (keys[index] == 0) {
        keys[index] = 1
        progressValue.value += weight
    }
}

@Composable
fun RequestButton(
    text:String,
    icon: ImageVector,
    left: Boolean,
    onClick: () -> Unit
){
    OutlinedButton(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = SecondColor,
            contentColor = ThirdColor
        ),
        border = null
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (left) Arrangement.Start else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (left) {
                Icon(
                    imageVector = icon,
                    contentDescription = "RequestAction"
                )
            }
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp, bottom = 10.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )
            if (!left) {
                Icon(
                    imageVector = icon,
                    contentDescription = "RequestAction"
                )
            }
        }
    }
}