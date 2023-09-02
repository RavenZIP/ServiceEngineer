package com.example.serviceengineer.screens.registerAndLogin

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.databaseSync
import com.example.serviceengineer.models.User
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*

private val timer = mutableStateOf(0)
private val email = mutableStateOf("")
private val password = mutableStateOf("")
private val userType = mutableStateOf("Сотрудник СЦ")
private val selected = mutableStateOf(true)
private val openTimer = mutableStateOf(false)
private lateinit var databaseUserData: DatabaseReference
private val timer2 = mutableStateOf(1)
private val check = mutableStateOf(false)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun RegistrationScreen(
    registrationCompleteClick: () -> Unit
){
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    val interactionSource = remember { MutableInteractionSource() }
    val error = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (modeModalLayout.value){
                "Выбор пользователя" -> SelectUserType()
                "Регистрация" -> RegistrationTimer()
            }
        },
        sheetBackgroundColor = MainColor,
        sheetElevation = 0.dp,
        sheetShape = RoundedTop
    ) {
        if (timer.value == 180) {
            openSheet(scope = coroutineScope)
            resetTimer()
            coroutineScope.launch {
                snackbarHostState.value.showSnackbar("Вы не успели подтвердить адрес электронной почты!")
            }
        }
        else if (key.value)
            fullReset()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 40.dp))

            Text(
                text = "Регистрация",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp, end = 20.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.padding(top = 25.dp))

            OutlinedTextFieldEmail(
                text = email,
                label = "Электронная почта",
                error,
                Icons.Outlined.Email,
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            OutlinedTextFieldPassword(
                text = password,
                label = "Пароль",
                error,
                Icons.Outlined.VpnKey,
                focusManager
            )

            Spacer(modifier = Modifier.padding(top = 15.dp))

            OutlinedTextFieldUserType()

            Spacer(modifier = Modifier.padding(top = 10.dp))

            FeaturesCard(
                Icon = Icons.Outlined.Feedback,
                TitleText = "Важно!",
                Text = "После нажатия кнопки продолжить у вас будет 3 минуты, чтобы подтвердить адрес электронной почты." +
                        "\n\nПосле подтверждения адреса электронной почты, вы автоматически попадете на главный экран приложения.",
                shapeRadius = 5
            )

            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    modifier = Modifier
                        .clip(RoundedTop)
                        .background(SecondColor)
                        .fillMaxWidth(),
                    backgroundColor = SecondColor,
                    elevation = 0.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.padding(bottom = 30.dp))

                        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                            CustomButton(
                                text = "Продолжить",
                                backgroundColor = ThirdColor.copy(0.7f),
                                borderWidth = 0.dp,
                                borderColor = SecondColor,
                                contentColor = MainColor
                            ) {
                                modeModalLayout.value = "Регистрация"
                                if (isEmailValid(email.value) && isPasswordValid(password.value))
                                    createNewUser(
                                        coroutineScope,
                                        context,
                                        snackbarHostState,
                                        registrationCompleteClick
                                    )
                                else
                                    error.value = true
                            }
                        }

                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
            }
        }
    }
    CustomSnackBar(snackbarHostState = snackbarHostState)
}

private fun resetTimer(){
    timer.value = 0
    mAuth.currentUser?.delete()
}

private fun fullReset(){
    timer.value = 0
    key.value = false
    openTimer.value = false
    email.value = ""
    password.value = ""
    mAuth.currentUser?.delete()
}

//Создание нового пользователя в базе данных Firebase
private fun createNewUser(
    coroutineScope: CoroutineScope,
    context: Context,
    snackbarHostState: MutableState<SnackbarHostState>,
    onClick: () -> Unit,
){
    mAuth.createUserWithEmailAndPassword(email.value, password.value).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            sendEmailVerify(coroutineScope, snackbarHostState)
            openTimer.value = true
            openSheet(scope = coroutineScope)
            coroutineScope.launch {
                while (timer.value != 180) {
                    if (!openTimer.value){
                        timer.value = 0
                        cancel()
                        break
                    }
                    else {
                        timeCheckEmailVerify(onClick, context, coroutineScope)
                        delay(1000)
                        timer.value += 1
                    }
                }
            }
        } else
            coroutineScope.launch {
                snackbarHostState.value.showSnackbar("Аккаунт с такой почтой уже существует!")
            }
    }
}

//Отправка на почту сообщения с подтверждением регистрации
private fun sendEmailVerify(
    coroutineScope: CoroutineScope,
    snackbarHostState: MutableState<SnackbarHostState>
){
    mAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
        if (task.isSuccessful)
            coroutineScope.launch {
                snackbarHostState.value.showSnackbar("На почту отправлена ссылка для подтверждения регистрации")
            }
        else
            coroutineScope.launch {
                snackbarHostState.value.showSnackbar("Ошибка отправки подтверждения электронной почты")
            }
    }
}

private fun timeCheckEmailVerify(
    onClick: () -> Unit,
    context: Context,
    coroutineScope: CoroutineScope
){
    mAuth.currentUser?.reload()
    if (mAuth.currentUser?.isEmailVerified == true){
        if (!check.value) {
            databaseUserData =
                FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
                    .child("UserData")
            val pushKey = databaseUserData.push().key.toString()
            val user = User(
                id = pushKey,
                name = "Нет данных",
                surname = "Нет данных",
                patronymic = "Нет данных",
                email = mAuth.currentUser!!.email.toString(),
                type = userType.value,
                job = "Нет данных",
                post = "Нет данных",
                license = ""
            )
            databaseUserData.setValue(user)
            databaseSync()
            coroutineScope.launch {
                while (timer2.value != 0) {
                    delay(1000)
                    timer2.value -= 1
                }
                onClick()
                openSheet(scope = coroutineScope)
            }
            check.value = true
        }
    }
}

@Composable
private fun OutlinedTextFieldUserType(){
    val scope = rememberCoroutineScope()
    OutlinedTextField(value = "Войти как\n" + userType.value,
        onValueChange = { newText ->
            userType.value = newText
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(shape = RoundedCornerShape(15))
            .clickable {
                modeModalLayout.value = "Выбор пользователя"
                openSheet(scope = scope)
            },
        readOnly = true,
        enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = activeText,
            disabledLeadingIconColor = activeText,
            disabledTrailingIconColor = activeText,
            disabledBorderColor = FourthColor,
            backgroundColor = SecondColor
        ),
        leadingIcon = { Icon(imageVector = Icons.Outlined.GridView, contentDescription = "userType") },
        trailingIcon = { Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "userTypeOpen") },
        shape = RoundedCornerShape(15)
    )
}

@Composable
private fun UserTypeCard(
    UserType: String,
    TitleText: String,
    Description: String,
    Color: Color,
    Selected: Boolean,
    scope: CoroutineScope
){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable {
                userType.value = UserType
                selected.value = Selected
                openSheet(scope = scope)
            },
        backgroundColor = MainColor,
        border = BorderStroke(2.dp, Color),
        elevation = 0.dp
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, end = 5.dp),
            contentAlignment = Alignment.TopEnd
        ){
            Icon(modifier = Modifier.size(15.dp),
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "checkType",
                tint = Color
            )
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = TitleText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = Description,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = TitleGrey
            )
        }
    }
}

@Composable
private fun TimerCard(Time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f),
        backgroundColor = MainColor,
        elevation = 0.dp
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Времени осталось",
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
            Text(
                text = "$Time сек.",
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
        }

    }
}

@Composable
fun SelectUserType() {
    val color1 = if (selected.value) ThirdColor else FourthColor
    val color2 = if (!selected.value) ThirdColor else FourthColor
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)

        Spacer(modifier = Modifier.padding(bottom = 15.dp))

        UserTypeCard(
            UserType = "Сотрудник СЦ",
            TitleText = "Сотрудник сервисного центра",
            Description = "Данный вариант предназначен для сотрудников сервисного центра\n\n" +
                    "После регистрации вы сможете указать ваш сервисный центр или создать его" +
                    " и у каждого сотрудника будет доступ к общим данным",
            Color = color1,
            Selected = true,
            scope = scope
        )

        Spacer(modifier = Modifier.padding(bottom = 15.dp))

        UserTypeCard(
            UserType = "Самозанятый",
            TitleText = "Самозанятый мастер",
            Description = "Данный вариант предназначен для тех, кто занимается ремонтом самостоятельно",
            Color = color2,
            Selected = false,
            scope = scope
        )

        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
private fun RegistrationTimer() {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)

        Spacer(modifier = Modifier.padding(bottom = 15.dp))

        TimerCard(Time = (180 - timer.value).toString())

        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
            CustomButton(
                text = "Отмена",
                backgroundColor = ThirdColor.copy(0.7f),
                borderWidth = 0.dp,
                borderColor = SecondColor,
                contentColor = MainColor
            ) {
                openTimer.value = false
                timer.value = 180
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}