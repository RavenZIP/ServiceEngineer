package com.example.serviceengineer.customui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.serviceengineer.screens.helpers.keys
import com.example.serviceengineer.screens.helpers.progressValue
import com.example.serviceengineer.ui.theme.*

@Composable
fun CustomOutlinedTextFieldSingleLine(
    value: MutableState<String>,
    text: String,
    weight: Float,
    id: Int,
    enabled: Boolean
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {
            newText ->
            value.value = newText
            if (keys[id] == 0 && value.value.isNotEmpty()) {
                keys[id] = 1
                progressValue.value += weight
            }
            else if (value.value.isEmpty()){
                keys[id] = 0
                progressValue.value -= weight
            }
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            unfocusedBorderColor = FourthColor,
            unfocusedLabelColor = ThirdColor.copy(0.8f),
            textColor = Color.Black,
            disabledBorderColor = FourthColor,
            disabledTextColor = activeText,
            disabledLabelColor = ThirdColor.copy(0.8f),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(15),
        singleLine = true,
        enabled = enabled
    )
}

@Composable
fun CustomOutlinedTextFieldSingleLine_v2(
    value: MutableState<String>,
    text: String
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {
                newText -> value.value = newText
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            disabledBorderColor = FourthColor,
            disabledLabelColor = ThirdColor.copy(0.8f),
            disabledTextColor = activeText,
            unfocusedBorderColor = FourthColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(15),
        singleLine = true
    )
}

@Composable
fun CustomOutlinedTextFieldMultiLine(
    value: MutableState<String>,
    text: String,
    minLines: Int,
    ImeAction: ImeAction,
    weight: Float,
    id: Int,
    shapePercent: Int,
    enabled: Boolean
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {
            newText ->
            value.value = newText
            if (keys[id] == 0 && value.value.isNotEmpty()) {
                keys[id]  = 1
                progressValue.value += weight
            }
            else if (value.value.isEmpty()){
                keys[id] = 0
                progressValue.value -= weight
            }
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            unfocusedBorderColor = FourthColor,
            unfocusedLabelColor = ThirdColor.copy(0.8f),
            textColor = Color.Black,
            disabledBorderColor = FourthColor,
            disabledTextColor = activeText,
            disabledLabelColor = ThirdColor.copy(0.8f),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction
        ),
        singleLine = false,
        shape = RoundedCornerShape(shapePercent),
        minLines = minLines,
        enabled = enabled
    )
}
@Composable
fun CustomOutlinedTextFieldMultiLine_v2(
    value: MutableState<String>,
    text: String,
    minLines: Int,
    ImeAction: ImeAction,
    shapePercent: Int,
){
    OutlinedTextField(
        value = value.value,
        onValueChange = {
                newText -> value.value = newText
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            disabledBorderColor = FourthColor,
            disabledLabelColor = ThirdColor.copy(0.8f),
            disabledTextColor = activeText,
            unfocusedBorderColor = FourthColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction
        ),
        singleLine = false,
        shape = RoundedCornerShape(shapePercent),
        minLines = minLines,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldWithDropDownMenu(
    value: MutableState<String>,
    list: MutableList<String>,
    text: String
){
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        Icons.Outlined.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Outlined.ArrowDropDown
    val interactionSource = remember { MutableInteractionSource() }

    Column() {
        OutlinedTextField(
            value = value.value,
            onValueChange = { newText ->
                value.value = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text(text) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = ThirdColor,
                focusedBorderColor = ThirdColor,
                focusedLabelColor = ThirdColor,
                backgroundColor = SecondColor,
                disabledBorderColor = FourthColor,
                disabledLabelColor = ThirdColor.copy(0.8f),
                disabledTextColor = activeText,
                unfocusedBorderColor = FourthColor
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
                Icon(icon, "contentDescription",
                    Modifier.clickable (
                        interactionSource = interactionSource,
                        indication = null
                    ) { expanded = !expanded })
            },
            shape = RoundedCornerShape(15),
            singleLine = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 0.dp, y = 5.dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .height(160.dp)
                .background(SecondColor.copy(0.9f))
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    value.value = label
                    expanded = false
                }) {
                    Text(text = label, color = activeText)
                }
            }
        }
    }
}


@Composable
fun CustomOutlinedTextFieldNumbers(
    value: MutableState<String>,
    text: String,
    weight: Float,
    id: Int,
    enabled: Boolean
){
    val pattern = remember { Regex("^\\d*\$") }
    OutlinedTextField(
        value = value.value,
        onValueChange = {
            newText ->
            if (newText.length <= 12 && newText.matches(pattern))
                value.value = newText
            if (keys[id] == 0 && value.value.isNotEmpty()) {
                keys[id]  = 1
                progressValue.value += weight
            }
            else if (value.value.isEmpty()){
                keys[id] = 0
                progressValue.value -= weight
            }
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            unfocusedBorderColor = FourthColor,
            unfocusedLabelColor = ThirdColor.copy(0.8f),
            textColor = Color.Black,
            disabledBorderColor = FourthColor,
            disabledTextColor = activeText,
            disabledLabelColor = ThirdColor.copy(0.8f),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone
        ),
        shape = RoundedCornerShape(15),
        singleLine = true,
        enabled = enabled
    )
}

@Composable
fun CustomOutlinedTextFieldPrice(
    value: MutableState<String>,
    text: String/*,
    isError: MutableState<Boolean>*/
){
    val pattern = remember { Regex("^\\d*\$") }
    OutlinedTextField(
        value = value.value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { newText ->
            if (newText.length <= 7 && newText.matches(pattern))
                value.value = newText
/*            if (isError.value)
                isError.value = false*/
        },
        textStyle = TextStyle.Default.copy(fontSize = 16.sp),
        label = {
            Text(text)
        },
        placeholder = { Text(text) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = ThirdColor,
            focusedBorderColor = ThirdColor,
            focusedLabelColor = ThirdColor,
            backgroundColor = SecondColor,
            disabledBorderColor = FourthColor,
            disabledLabelColor = ThirdColor.copy(0.8f),
            disabledTextColor = activeText,
            unfocusedBorderColor = FourthColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone
        ),
        singleLine = true,
        //isError = isError.value,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBasicTextField(
    text: MutableState<String>,
    fontSize: Int,
    placeholder: String
){
    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it },
        textStyle = TextStyle(fontSize = fontSize.sp),
        modifier = Modifier.fillMaxWidth(),
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            value = text.value,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            placeholder = { Text(text = placeholder, fontSize = fontSize.sp, color = activeText.copy(0.5f)) },
            singleLine = true,
            enabled = true,
            interactionSource = MutableInteractionSource(),
            contentPadding = PaddingValues(0.dp)
        )
    }
}

/* BasicTextField(
        value.value,
        onValueChange = { value.value = it },
        modifier = Modifier.fillMaxWidth(0.85f),
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(percent = 10))
                    .padding(13.dp)
            ){
                innerTextField()
            }
        }
    )*/
