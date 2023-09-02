package com.example.serviceengineer.screens.registerAndLogin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.openSheet
import com.example.serviceengineer.ui.theme.*
import kotlinx.coroutines.CoroutineScope

@Composable
fun OutlinedTextFieldEmail(
    text: MutableState<String>,
    label: String,
    isError: MutableState<Boolean>,
    Icon: ImageVector
){
    val color = remember { mutableStateOf(activeText) }
    OutlinedTextField(value = text.value,
        onValueChange = { newText ->
            text.value = newText
            if (isError.value)
                isError.value = false
        },
        modifier = Modifier.fillMaxWidth(0.9f).onFocusChanged
        {
            if (it.isFocused)
                color.value = ThirdColor
            else
                color.value = activeText
        },
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            unfocusedBorderColor = FourthColor,
            leadingIconColor = color.value,
            errorLeadingIconColor = error
        ),
        isError = isError.value,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icon, contentDescription = "email") },
        shape = RoundedCornerShape(15)
    )
}

@Composable
fun OutlinedTextFieldPassword(
    text: MutableState<String>,
    label: String,
    isError: MutableState<Boolean>,
    Icon: ImageVector,
    focusState: FocusManager
){
    val color = remember { mutableStateOf(activeText) }
    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(value = text.value,
        onValueChange = { newText ->
            text.value = newText
            if (isError.value)
                isError.value = false
        },
        modifier = Modifier.fillMaxWidth(0.9f).onFocusChanged
        {
            if (it.isFocused)
                color.value = ThirdColor
            else
                color.value = activeText
        },
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusState.clearFocus() }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            unfocusedBorderColor = FourthColor,
            leadingIconColor = color.value,
            errorLeadingIconColor = error
        ),
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isError.value,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icon, contentDescription = "password") },
        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Outlined.Visibility
            else Icons.Outlined.VisibilityOff
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }){
                Icon(imageVector  = image, "")
            }
        },
        shape = RoundedCornerShape(15)
    )
}

@Composable
fun CustomButton(
    text: String,
    borderWidth: Dp,
    borderColor: Color,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
){
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() },
        shape = RoundedCornerShape(15),
        border = BorderStroke(borderWidth, borderColor),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            fontSize = 16.sp,
            letterSpacing = 0.sp
        )
    }
}
