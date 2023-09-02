package com.example.serviceengineer.screens.user

import android.service.autofill.UserData
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.BuildConfig
import com.example.serviceengineer.customui.Style1
import com.example.serviceengineer.screens.helpers.ServerVersion
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.mAuth
import com.example.serviceengineer.screens.helpers.modeModalLayout
import com.example.serviceengineer.screens.helpers.openSheet
import com.example.serviceengineer.screens.helpers.sheetState
import com.example.serviceengineer.screens.helpers.snackbarHostState
import com.example.serviceengineer.screens.helpers.uName
import com.example.serviceengineer.screens.helpers.uPatronymic
import com.example.serviceengineer.screens.helpers.uSurname
import com.example.serviceengineer.screens.helpers.uType
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.activeText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Settings(){
    val coroutineScope = rememberCoroutineScope()
    TitleAppBar.value = "Настройки"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Аккаунт",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),

        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Style1(
            if (uSurname.value != "Нет данных" && uName.value != "Нет данных" && uPatronymic.value != "Нет данных")
                uSurname.value + " " + uName.value + " " + uPatronymic.value
            else if (uSurname.value != "Нет данных" && uName.value != "Нет данных")
                uSurname.value + " " + uName.value
            else
                "Личные данные",
            "Нажмите, чтобы отредактировать",
            Icons.Outlined.ContactPage,
            "SNP",
            onClickAction = {
                modeModalLayout.value = "Личные данные"
                openSheet(scope = coroutineScope)
            },
            tint = activeText,
            backgroundColor = MainColor
        )
        Style1("Безопасность",
            "Адрес электронной почты и пароль",
            Icons.Outlined.Lock,
            "Safety",
            onClickAction = { },
            tint = activeText,
            backgroundColor = MainColor
        )
        Style1("Тип аккаунта",
            "Изменить тип аккаунта",
            Icons.Outlined.ManageAccounts,
            "ManageAccounts",
            onClickAction = {
                if (uType.value == "Самозанятый") uType.value = "Сотрудник СЦ" else uType.value = "Самозанятый"
                val databaseUserData = FirebaseDatabase.getInstance().getReference("Users").child(
                    mAuth.currentUser!!.uid).child("UserData")
                val data = mapOf("type" to uType.value)
                databaseUserData.updateChildren(data)
            },
            tint = activeText,
            backgroundColor = MainColor
        )
        Style1("Вид работ",
            "Выберите какую технику вы будете обслуживать",
            Icons.Outlined.WorkOutline,
            "WorkType",
            onClickAction = { },
            tint = activeText,
            backgroundColor = MainColor
        )
        if (uType.value == "Сотрудник СЦ") {
            Style1(
                "Больше информации",
                "Включить дополнительные пункты в справочнике",
                Icons.Outlined.Book,
                "Book",
                onClickAction = { },
                tint = activeText,
                backgroundColor = MainColor
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Внешний вид",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Style1("Тема",
            "Изменить цвета приложения",
            Icons.Outlined.Palette,
            "Themes",
            onClickAction = { },
            tint = activeText,
            backgroundColor = MainColor
        )
        /*Style1("Отображение статистики",
            "Изменить параметры отображения некоторых блоков со статистикой",
            Icons.Outlined.ShowChart,
            "ChartSettings",
            onClickAction = { },
            tint = activeText,
            backgroundColor = MainColor
        )*/
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Обновления",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Style1("Проверить наличие обновлений",
            "",
            Icons.Outlined.FileUpload,
            "DownloadUpdate",
            onClickAction = {
                if (ServerVersion.value == BuildConfig.VERSION_NAME) {
                    coroutineScope.launch {
                        snackbarHostState.value.showSnackbar("Новых обновлений нет!")
                    }
                } else {
                    coroutineScope.launch {
                        snackbarHostState.value.showSnackbar("Доступна новая версия!")
                    }
                }
            },
            tint = activeText,
            backgroundColor = MainColor
        )
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
}