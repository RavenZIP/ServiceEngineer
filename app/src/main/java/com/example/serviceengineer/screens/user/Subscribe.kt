package com.example.serviceengineer.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.registerAndLogin.FeaturesCard
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.TitleGrey
import com.example.serviceengineer.ui.theme.activeText

@Composable
fun Subscribe(
    payCardClick: () -> Unit,
    payQIWIClick: () -> Unit

){
    TitleAppBar.value = "Подписка"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Box(modifier = Modifier
            .clip(RoundedCornerShape(15))
            .background(SecondColor)
        ){
            Icon(
                imageVector = Icons.Outlined.Verified,
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp),
                tint = ThirdColor.copy(0.8f)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Спасибо!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            text = "Выражаем благодарность за использование нашего продукта",
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            letterSpacing = 0.sp,
            color = TitleGrey,
            modifier = Modifier.fillMaxWidth(0.85f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Card(
            modifier = Modifier.fillMaxWidth(0.6f),
            backgroundColor = SecondColor,
            shape = RoundedCornerShape(15),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Подписка активна до",
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "25.05.2023",
                    letterSpacing = 0.5.sp,
                    color = ThirdColor.copy(0.8f)
                )
            }
        }
        FeaturesCard(
            Icon = Icons.Outlined.Feedback,
            TitleText = "Пожалуйста, прочтите!",
            Text = "Подписка выдается не на устройство, а на аккаунт - поэтому статус не сбрасывается при обновлении, " +
                    "переустановке или смене устройства\n\n" +
                    "Это НЕ одноразовая оплата, а 1, 3, 6 - месячная или годовая подписка. Длительность подписки " +
                    "выбирается суммой, которую вы переведете. Тарифы представлены ниже:\n\n" +
                    "1 месяц - 150 рублей\n" +
                    "3 месяца - 405 рублей (скидка 10%)\n" +
                    "6 месяцев - 720 рублей (скидка 20%)\n" +
                    "12 месяцев - 1260 рублей (скидка 30%)",
            shapeRadius = 5
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        PayButton(
            TitleText = "Перевод на карту",
            Text = "Перевод на карту Сбербанка или Тинькофф банка"
        ) {
            payCardClick()
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        PayButton(
            TitleText = "QIWI",
            Text = "Альтернативный способ, если по каким-либо причинам перевод на карту не возможен"
        ) {
            payQIWIClick()
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
private fun PayButton(
    TitleText: String,
    Text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = { onClick() },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = TitleText,
                fontSize = 18.sp,
                color = activeText,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Text(
                modifier = Modifier.padding(end = 30.dp),
                text = Text,
                color = TitleGrey,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.5.sp
            )
        }
    }
}