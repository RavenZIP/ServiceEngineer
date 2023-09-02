package com.example.serviceengineer.screens.user

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.snackbarHostState
import com.example.serviceengineer.screens.registerAndLogin.FeaturesCard
import kotlinx.coroutines.launch


@Composable
fun PayQiwi(){
    TitleAppBar.value = "QIWI"
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        FeaturesCard(
            Icon = Icons.Outlined.Subject,
            TitleText = "Инструкция",
            Text = "1. Откройте приложение QIWI, в разделе «Платежи и переводы» выберите «Все», затем «Переводы между кошельками»\n\n" +
                    "2. В списке выберите «ЮMoney», в пункте «Номер счета» вставьте номер, который можно получить по нажатию кнопки " +
                    "«Скопировать номер»\n\n" +
                    "3. Сохраните выписку из банка/чек/скриншот и отправьте его вместе с числовым ID  аккаунта (кнопка «Скопировать ID аккаунта») " +
                    "разработчику (кнопка «Написать разработчику»)",
            shapeRadius = 5
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        IconWithText(Icons.Outlined.CopyAll, "Скопировать номер кошелька"){
            val clipboard = getSystemService(context, ClipboardManager::class.java) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("Номер кошелька", "0000000000000000")
            clipboard.setPrimaryClip(clip)
            scope.launch {
                snackbarHostState.value.showSnackbar("Номер кошелька скопирован!")
            }
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        IconWithText(Icons.Outlined.CopyAll, "Скопировать ID аккаунта"){
            val clipboard = getSystemService(context, ClipboardManager::class.java) as ClipboardManager
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