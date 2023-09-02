package com.example.serviceengineer.screens.main

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.customui.Style1
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val TitleText = mutableStateOf("")
private lateinit var databaseCompany: DatabaseReference
private lateinit var databaseUserData: DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfile(
    subscribeButton: () -> Unit,
    organizationButton:() -> Unit,
    aboutAppButton: () -> Unit,
    helpButton: () -> Unit,
    settingsButton: () -> Unit
) {
    TitleAppBar.value = "Профиль"

    databaseCompany = FirebaseDatabase.getInstance().getReference("Organizations")
    databaseUserData =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("UserData")

    val coroutineScope = rememberCoroutineScope()
    var timer = 1
    val context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    val intent: Intent? = packageManager.getLaunchIntentForPackage(context.packageName)
    val componentName: ComponentName? = intent?.component
    val mainIntent: Intent = Intent.makeRestartActivityTask(componentName)

    if (uName.value == "Нет данных" && uSurname.value == "Нет данных")
        TitleText.value = uEmail.value
    else
        TitleText.value = uSurname.value + " " + uName.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "",
                tint = ThirdColor.copy(0.8f),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(SecondColor)
                    .padding(15.dp)
                    .size(60.dp)
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Text(
                text = TitleText.value,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
            Text(
                text = uType.value,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            Style1(title = "Статус подписки",
                description = "Спасибо за поддержку проекта!",
                icon = Icons.Outlined.Verified,
                "Subscription",
                onClickAction = {
                    subscribeButton()
                },
                tint = ThirdColor.copy(0.8f),
                backgroundColor = SecondColor
            )
            if (uType.value == "Сотрудник СЦ") {
                Style1(
                    title = "Работа",
                    description =
                    when (uPost.value) {
                        "Администратор" -> "Управление организацией"
                        "Инженер" -> "Вы состоите в организации ${uJob.value}"
                        "Инженер (не подтверждено)" -> "Ожидайте подтверждения"
                        else -> "Связь с организацией"
                    },
                    icon = Icons.Outlined.CorporateFare,
                    "Work",
                    onClickAction = {
                        if (uJob.value == "Нет данных") {
                            modeModalLayout.value = "Организация"
                            mode.value = ""
                            openSheet(scope = coroutineScope)
                        } else if (uPost.value == "Администратор") {
                            organizationButton()
                        } else {
                            //
                        }
                    },
                    tint = ThirdColor,
                    backgroundColor = SecondColor
                )
            }
            Style1(title = "Настройки",
                description = "Параметры аккаунта, вид приложения и функции",
                icon = Icons.Outlined.Settings,
                "Settings",
                onClickAction = {
                    settingsButton()
                },
                tint = ThirdColor.copy(0.8f),
                backgroundColor = SecondColor
            )
            Style1(
                title = "Помощь",
                description = "Ответы на вопросы по работе приложения",
                icon = Icons.Outlined.HelpOutline,
                "Help",
                onClickAction = {
                    helpButton()
                },
                tint = ThirdColor.copy(0.8f),
                backgroundColor = SecondColor
            )
            Style1(
                title = "О приложении",
                description = "Описание приложения и его версия",
                icon = Icons.Outlined.Info,
                "AboutProgram",
                onClickAction = {
                    aboutAppButton()
                },
                tint = ThirdColor.copy(0.8f),
                backgroundColor = SecondColor
            )
            Style1(
                title = "Выход",
                description = "Выполнить выход из аккаунта",
                icon = Icons.Outlined.Logout,
                "Exit",
                onClickAction = {
                    mAuth.signOut()
                    coroutineScope.launch {
                        while (timer != 0){
                            delay(1000)
                            timer -= 1
                        }
                        context.startActivity(mainIntent)
                        Runtime.getRuntime().exit(0)
                    }
                },
                tint = testColor,
                backgroundColor = SecondColor
            )
            Spacer(modifier = Modifier.padding(bottom = 90.dp))
        }
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
}