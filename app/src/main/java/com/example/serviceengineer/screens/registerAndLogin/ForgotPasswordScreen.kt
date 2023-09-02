package com.example.serviceengineer.screens.registerAndLogin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.CustomSnackBar
import com.example.serviceengineer.screens.helpers.isEmailValid
import com.example.serviceengineer.screens.helpers.isPasswordValid
import com.example.serviceengineer.screens.helpers.mAuth
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.RoundedTop
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(

){
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    val email = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

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
            text = "Сброс и восстановление",
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

        FeaturesCard(
            Icon = Icons.Outlined.Feedback,
            TitleText = "Важно!",
            Text = "После нажатия кнопки продолжить вам на почту придет письмо для сброса пароля." +
                    "\n\nЕсли вы действительно хотите выполнить сброс пароля, то перейдите по ссылке в письме." +
                    " После этого вам необходимо задать новый пароль.",
            shapeRadius = 5
        )

        Box(modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(modifier = Modifier
                .clip(RoundedTop)
                .background(SecondColor)
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
                            if (isEmailValid(email.value))
                                mAuth.sendPasswordResetEmail(email.value).addOnCompleteListener {
                                    if (it.isSuccessful){
                                        coroutineScope.launch {
                                            snackbarHostState.value.showSnackbar("Письмо для сброса пароля отправлено на почту!")
                                        }
                                    }
                                    else
                                        coroutineScope.launch {
                                            snackbarHostState.value.showSnackbar("Ошибка сброса пароля. Проверьте " +
                                                    "наличие интернет соединения или правильность введенного email")
                                        }
                                }
                            else
                                error.value = true
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                }
            }
        }
    }
    CustomSnackBar(snackbarHostState = snackbarHostState)
}