package com.example.serviceengineer.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.BuildConfig
import com.example.serviceengineer.R
import com.example.serviceengineer.customui.AboutAppStyle
import com.example.serviceengineer.customui.Style5
import com.example.serviceengineer.screens.helpers.TitleAppBar

@Composable
fun AboutApp(){
    TitleAppBar.value = "О приложении"
    Column(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AboutAppStyle(
            painterResource(R.drawable.icon_v2),
            "Service Engineer",
            "Версия: ${BuildConfig.VERSION_NAME}"
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Style5(
            "Telegram-канал приложения",
            "Обновления и новости",
            "https://t.me/+ZE5TdOBEW8FjZGFi"
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Style5(
            "Telegram чат-бот",
            "Справочник по приложению",
            "https://t.me/mobile_service_engineer_bot"
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}