package com.example.serviceengineer.screens.user

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat.IconType
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.snackbarHostState
import com.example.serviceengineer.screens.registerAndLogin.FeaturesCard
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.TitleGrey
import com.example.serviceengineer.ui.theme.activeText
import kotlinx.coroutines.launch

@Composable
fun PayCard(){
    TitleAppBar.value = "Перевод на карту"
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        FeaturesCard(
            Icon = Icons.Outlined.Subject,
            TitleText = "Инструкция",
            Text = "1. Нажмите «Скопировать номер» и переведите деньги через сайт/приложение вашего банка\n\n" +
                    "2. Сохраните чек/выписку и отправьте вместе с числовым ID аккаунта (кнопка «Скопировать ID аккаунта»)" +
                    " разработчику (кнопка «Написать разработчику»)",
            shapeRadius = 5
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        IconWithText(Icons.Outlined.CopyAll, "Скопировать номер карты"){
            val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("Номер карты", "0000000000000000")
            clipboard.setPrimaryClip(clip)
            scope.launch {
                snackbarHostState.value.showSnackbar("Номер карты скопирован!")
            }
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        IconWithText(Icons.Outlined.CopyAll, "Скопировать ID аккаунта"){
            val clipboard = ContextCompat.getSystemService(
                context,
                ClipboardManager::class.java
            ) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("ID аккаунта", cUser!!.uid)
            clipboard.setPrimaryClip(clip)
            scope.launch {
                snackbarHostState.value.showSnackbar("ID аккаунта скопирован!")
            }
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        IconWithText(Icons.Outlined.Edit, "Написать разработчику"){

        }
    }
}