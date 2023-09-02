package com.example.serviceengineer.screens.helpers

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.serviceengineer.models.*
import com.example.serviceengineer.navigation.BottomBarScreen
import com.example.serviceengineer.navigation.HomeScreenNavGraph
import com.example.serviceengineer.screens.main.WorkMenu
import com.example.serviceengineer.screens.registerAndLogin.SelectUserType
import com.example.serviceengineer.ui.theme.*
import com.google.firebase.database.*

private lateinit var databasePriceList: DatabaseReference
private lateinit var databaseParameters: DatabaseReference
private lateinit var databaseUserData: DatabaseReference
private lateinit var databaseCompany: DatabaseReference
private lateinit var databaseSparePartsList: DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()){
    databasePriceList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("PriceList")
    databaseParameters =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Parameters")
    databaseSparePartsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("SpareParts")
    databaseUserData =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("UserData")
    databaseCompany =
        FirebaseDatabase.getInstance().getReference("Organizations")
    if (modeModalLayout.value == "Регистрация" || modeModalLayout.value ==  "Выбор пользователя")
        modeModalLayout.value = "Ремонт"

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (modeModalLayout.value){
                "Выбор пользователя" -> SelectUserType()
                "Ремонт" -> WorkMenu()
                "Заявка" -> SelectRequest()
                "Выход" -> SaveAndExitOrExitOnly()
                "Тип устройства" -> SelectDeviceType()
                "Производитель" -> SelectDeviceManufacturer()
                "Модель" -> SelectDeviceModel()
                "Обнаруженная неисправность", "Заявленная неисправность" -> SelectDeviceFault()
                "Клиенты" -> SelectClient()
                "Добавить услугу", "Редактирование услуги" -> ServiceAdd(databasePriceList)
                "Добавить значение", "Редактирование значение" -> ParameterAdd(database = databaseParameters)
                "Добавить запчасть", "Редактирование запчасти" -> SparePartAdd(databaseSparePartsList)
                "Личные данные" -> EditSNP(databaseUserData)
                "Организация" -> Organization(databaseCompany, databaseUserData)
                "Оказанные услуги" -> SelectServices()
                "Потраченные запчасти" -> SelectSpareParts()
                "Загрузка схемы" -> SetImageInfo()
            }
        },
        sheetBackgroundColor = MainColor,
        sheetElevation = 0.dp,
        sheetShape = RoundedTop
    ) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar(navController = navController) }
        ) {
            HomeScreenNavGraph(navController = navController)
        }
    }
    CustomSnackBar(snackbarHostState = snackbarHostState)
}

@Composable
fun SearchField(width: Float){
    var value by remember { mutableStateOf(TextFieldValue("")) }
    BasicTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth(width)
            .onFocusChanged {
                if (it.isFocused)
                    placeholderTopAppBar.value = ""
                else {
                    if (TitleAppBar.value == "Клиенты" || modeModalLayout.value == "Клиенты")
                        placeholderTopAppBar.value = "Поиск клиента..."
                    else if (TitleAppBar.value == "Запчасти" || modeModalLayout.value == "Потраченные запчасти")
                        placeholderTopAppBar.value = "Поиск запчастей..."
                    else if (TitleAppBar.value == "Заявки" || modeModalLayout.value == "Заявка")
                        placeholderTopAppBar.value = "Поиск заявок..."
                    else if (TitleAppBar.value == "Отчеты")
                        placeholderTopAppBar.value = "Поиск отчетов..."
                    else if (TitleAppBar.value == "Заметки")
                        placeholderTopAppBar.value = "Поиск заметок..."
                    else if (TitleAppBar.value == "Прайс-лист" || modeModalLayout.value == "Оказанные услуги")
                        placeholderTopAppBar.value = "Поиск услуг..."
                    else if (TitleAppBar.value == "Схемы")
                        placeholderTopAppBar.value = "Поиск схемы..."
                    else if (TitleAppBar.value == "Программы")
                        placeholderTopAppBar.value = "Поиск программы..."
                    else if (TitleAppBar.value == "Оборудование")
                        placeholderTopAppBar.value = "Поиск оборудования..."
                    else if (TitleAppBar.value == "Инструкции")
                        placeholderTopAppBar.value = "Поиск инструкции..."
                    else if (modeModalLayout.value == "Обнаруженная неисправность"
                        || modeModalLayout.value == "Заявленная неисправность"
                    )
                        placeholderTopAppBar.value = "Поиск неисправности..."
                    else if (modeModalLayout.value == "Тип устройства")
                        placeholderTopAppBar.value = "Поиск устройства..."
                    else if (modeModalLayout.value == "Производитель")
                        placeholderTopAppBar.value = "Поиск производителя..."
                    else if (modeModalLayout.value == "Модель")
                        placeholderTopAppBar.value = "Поиск модели..."
                    else if (modeModalLayout.value == "Организация")
                        placeholderTopAppBar.value = "Поиск организации..."
                }
            },
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        ),
        decorationBox = { innerTextField ->
            Row(modifier = Modifier
                .clip(RoundedCornerShape(percent = 20))
                .background(color = SecondColor)
                .padding(10.dp)
            ){
                Box(modifier = Modifier.padding(end = 10.dp)){
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "search",
                        tint = ThirdColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholderTopAppBar.value,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = ThirdColor.copy(0.7f)
                    )
                }
                innerTextField()
            }
        },
        cursorBrush = SolidColor(ThirdColor)
    )
}

@Composable
private fun TopBar(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(if (TitleAppBar.value == "Настройки") SecondColor else MainColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
        Box(modifier = Modifier
            .weight(1f)
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)) {
            if (TitleAppBar.value == "Заявки" ||
                TitleAppBar.value == "Отчеты" ||
                TitleAppBar.value == "Прайс-лист" ||
                TitleAppBar.value == "Клиенты" ||
                TitleAppBar.value == "Запчасти" ||
                TitleAppBar.value == "Заметки" ||
                TitleAppBar.value == "Схемы" && schemeListIsVisible.value ||
                TitleAppBar.value == "Программы" ||
                TitleAppBar.value == "Оборудование" ||
                TitleAppBar.value == "Инструкции"
            ){
                SearchField(0.9f)
            }
            else
                Text(
                    text = TitleAppBar.value,
                    fontSize = if (TitleAppBar.value != "Настройки") 25.sp else 23.sp,
                    fontWeight = FontWeight.Medium,
                )
        }
        Box(
            modifier = Modifier
                .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(15))
                .clickable { onClickTopBarButton.value() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                when (TitleAppBar.value) {
                    "О клиенте" -> Icons.Outlined.Edit
                    "Создание отчета" -> Icons.Outlined.Close
                    "Заявка", "Отчет", "Прайс-лист", "Запчасти" -> Icons.Outlined.FileOpen
                    else -> authIcon.value
                },
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Clients,
        BottomBarScreen.Book,
        BottomBarScreen.Work,
        BottomBarScreen.UserProfile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation (
            backgroundColor = SecondColor,
            modifier = Modifier
                .shadow(5.dp)
                .height(80.dp)
                //.clip(RoundedTop)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val background = if (selected) FourthColor else SecondColor
    val tint = if (selected) ThirdColor.copy(0.8f) else TitleGrey

    Box(modifier = Modifier
        .size(45.dp)
        .clip(RoundedCornerShape(15))
        .background(background)
        .clickable(onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().navigatorName) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = screen.icon,
            contentDescription = "Navigation Icon",
            tint = tint,
            modifier = Modifier.size(25.dp)
        )
    }
}