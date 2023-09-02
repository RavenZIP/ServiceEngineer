package com.example.serviceengineer.screens.registerAndLogin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.ui.theme.*

val key = mutableStateOf(false)

@Composable
fun WelcomeScreen(
    registrationClick: () -> Unit,
    signInClick: () -> Unit
){
    key.value = true
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MainColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))

        Text(
            text = "Добро пожаловать в \nService Engineer",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp, end = 20.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))

        FeaturesCard(
            Icon = Icons.Outlined.Groups,
            TitleText = "Для всех",
            Text = "Ориентир как на самозанятых мастеров, так и на сотрудников сервисного центра",
            shapeRadius = 10
        )
        FeaturesCard(
            Icon = Icons.Outlined.Lightbulb,
            TitleText = "База знаний",
            Text = "Обширная база данных с необходимой для ремонта информацией",
            shapeRadius = 10
        )
        FeaturesCard(
            Icon = Icons.Outlined.ViewInAr,
            TitleText = "Многофункциональность",
            Text = "Составление заявок и отчетов, создание прайс-листа и " +
                    "списка запчастей, добавление клиентов и др.",
            shapeRadius = 10
        )

        Box(modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(modifier = Modifier
                .clip(RoundedTop)
                .fillMaxWidth(),
                backgroundColor = SecondColor,
                elevation = 0.dp
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.padding(bottom = 30.dp))
                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                        CustomButton(
                            text = "Регистрация",
                            backgroundColor = ThirdColor.copy(0.7f),
                            borderWidth = 0.dp,
                            borderColor = SecondColor,
                            contentColor = MainColor
                        ) {
                            registrationClick()
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                        CustomButton(
                            text = "Вход",
                            backgroundColor = FourthColor,
                            borderWidth = 0.dp,
                            borderColor = SecondColor,
                            contentColor = activeText
                        ) {
                            signInClick()
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                }
            }
        }
    }
}

@Composable
fun FeaturesCard(
    Icon: ImageVector,
    TitleText: String,
    Text: String,
    shapeRadius: Int
){
    Card(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(top = 15.dp),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(shapeRadius),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Icon(
                Icon,
                contentDescription = "",
                tint = ThirdColor.copy(0.8f),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(text = TitleText,
                fontSize = 18.sp,
                color = activeText,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(modifier = Modifier.padding(end = 20.dp),
                text = Text,
                color = TitleGrey,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}
