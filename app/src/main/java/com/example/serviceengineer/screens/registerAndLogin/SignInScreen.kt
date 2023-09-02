package com.example.serviceengineer.screens.registerAndLogin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.databaseSync
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    signInCompleteClick: () -> Unit,
    forgotPasswordClick: () -> Unit
){
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val timer = remember { mutableStateOf(1) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MainColor).clickable (
            interactionSource = interactionSource,
            indication = null
        ){
            keyboardController?.hide()
            focusManager.clearFocus()
          },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 40.dp))

        Text(
            text = "Войти в аккаунт",
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
            Icons.Outlined.Email
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))

        OutlinedTextFieldPassword(
            text = password,
            label = "Пароль",
            error,
            Icons.Outlined.VpnKey,
            focusManager
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
                            text = "Продолжить",
                            backgroundColor = ThirdColor.copy(0.7f),
                            borderWidth = 0.dp,
                            borderColor = SecondColor,
                            contentColor = MainColor
                        ) {
                            if (isEmailValid(email.value) && isPasswordValid(password.value))
                                signInAccount(
                                    coroutineScope,
                                    signInCompleteClick,
                                    snackbarHostState,
                                    email,
                                    password,
                                    timer
                                )
                            else
                                error.value = true
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                        CustomButton(
                            text = "Забыли пароль?",
                            backgroundColor = FourthColor,
                            borderWidth = 0.dp,
                            borderColor = SecondColor,
                            contentColor = activeText
                        ) {
                            forgotPasswordClick()
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                }
            }
        }
    }
    CustomSnackBar(snackbarHostState = snackbarHostState)
}

private fun signInAccount(
    coroutineScope: CoroutineScope,
    onClick: () -> Unit,
    snackbarHostState: MutableState<SnackbarHostState>,
    email: MutableState<String>,
    password: MutableState<String>,
    timer: MutableState<Int>,
){
    mAuth.signInWithEmailAndPassword(email.value, password.value).addOnCompleteListener{
            task ->
        if (task.isSuccessful) {
            mAuth.currentUser?.reload()
            if (mAuth.currentUser?.isEmailVerified == true) {
                databaseSync()
                coroutineScope.launch {
                    while (timer.value != 0) {
                        delay(1000)
                        timer.value -= 1
                    }
                    onClick()
                }
            }
            else
                coroutineScope.launch {
                    snackbarHostState.value.showSnackbar("Вы не подтвердили адрес электронной почты!")
                }
        }
        else
            coroutineScope.launch {
                snackbarHostState.value.showSnackbar("Некорректный адрес электронной почты или пароль!")
            }
    }
}

