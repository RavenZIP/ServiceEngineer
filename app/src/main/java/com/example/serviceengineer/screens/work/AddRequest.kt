package com.example.serviceengineer.screens.work

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.customui.CustomOutlinedTextFieldMultiLine
import com.example.serviceengineer.customui.CustomOutlinedTextFieldNumbers
import com.example.serviceengineer.customui.CustomOutlinedTextFieldSingleLine
import com.example.serviceengineer.models.Request
import com.example.serviceengineer.models.Client
import com.example.serviceengineer.models.Parameter
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var databaseRequests: DatabaseReference
private lateinit var databaseClients: DatabaseReference
private lateinit var databaseParameters: DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddRequest(
    nextStepClick:() -> Unit
){
    TitleAppBar.value = "Заявка"
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()
            Option(
                value = deviceType,
                text = "Тип устройства",
                weight = 0.1f,
                id = 0,
                enabled = true
            ) {
                modeModalLayout.value = "Тип устройства"
                openSheet(scope = scope)
            }
            Option(
                value = deviceManufacturer,
                text = "Производитель/Бренд",
                weight = 0.1f,
                id = 1,
                enabled = true
            ) {
                modeModalLayout.value = "Производитель"
                openSheet(scope = scope)
            }
            Option(
                value = deviceModel,
                text = "Модель",
                weight = 0.1f,
                id = 2,
                enabled = true
            ) {
                modeModalLayout.value = "Модель"
                openSheet(scope = scope)
            }
            Option(
                value = deviceFault,
                text = "Заявленная неисправность",
                weight = 0.1f,
                id = 3,
                enabled = true
            ) {
                modeModalLayout.value = "Заявленная неисправность"
                openSheet(scope = scope)
            }
            CustomOutlinedTextFieldMultiLine(
                value = deviceKit,
                text = "Комплектация",
                minLines = 3,
                ImeAction = ImeAction.Next,
                weight = 0.1f,
                id = 4,
                shapePercent = 10,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldMultiLine(
                value = deviceAppearance,
                text = "Внешний вид устройства",
                minLines = 3,
                ImeAction = ImeAction.Done,
                weight = 0.1f,
                id = 5,
                shapePercent = 10,
                enabled = true
            )
        }
        RequestButton (
            text = "Следующий этап",
            icon = Icons.Outlined.East,
            false
        ){
            nextStepClick()
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            scope.launch {
                sheetState.hide()
            }
        }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextStep(
    saveAndExit:() -> Unit
){
    databaseRequests = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Requests")
    databaseClients = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Clients")
    databaseParameters = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Parameters")
    val requestDate = SimpleDateFormat(
        "dd MMMM yyyy, HH:mm", Locale.getDefault()
    )
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MainColor)
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()

            CustomOutlinedTextFieldSingleLine(
                value = clientSurname,
                text = "Фамилия",
                weight = 0.1f,
                id = 6,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientName,
                text = "Имя",
                weight = 0.1f,
                id = 7,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientPatronymic,
                text = "Отчество",
                weight = 0.1f,
                id = 8,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldNumbers(
                value = clientPhone,
                text = "Номер телефона",
                weight = 0.1f,
                id = 9,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(15.dp))
        }
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(15))
                    .background(SecondColor)
                    .clickable {
                        modeModalLayout.value = "Клиенты"
                        openSheet(scope = scope)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.HowToReg,
                    contentDescription = "",
                    tint = ThirdColor,
                    modifier = Modifier.size(25.dp)
                )
            }
            RequestButton(
                text = "Завершить",
                icon = Icons.Outlined.Done,
                false
            ) {
                val pushKey = databaseRequests.push().key.toString()
                val param1Key = databaseRequests.push().key.toString()
                val param2Key = databaseRequests.push().key.toString()
                val param3Key = databaseRequests.push().key.toString()
                val param4Key = databaseRequests.push().key.toString()
                val keys = mutableListOf(
                    false,
                    false,
                    false,
                    false
                )
                val client = Client(
                    clientName = clientName.value.trim(),
                    clientSurname = clientSurname.value.trim(),
                    clientPatronymic = clientPatronymic.value.trim(),
                    clientPhone = clientPhone.value.trim()
                )
                val request = Request(
                    id = pushKey,
                    finished = false,
                    deviceType = deviceType.value.trim(),
                    deviceManufacturer = deviceManufacturer.value.trim(),
                    deviceModel = deviceModel.value.trim(),
                    deviceFault = deviceFault.value.trim(),
                    deviceKit = deviceKit.value.trim(),
                    deviceAppearance = deviceAppearance.value.trim(),
                    //clientPhone = clientPhone.value.trim(),
                    client = client,
                    requestDate = requestDate.format(Date())
                )
                val param1 = Parameter(
                    id = param1Key,
                    paramName = deviceType.value.trim(),
                    paramType = "Тип устройства"
                )
                val param2 = Parameter(
                    id = param2Key,
                    paramName = deviceManufacturer.value.trim(),
                    paramType = "Производитель"
                )
                val param3 = Parameter(
                    id = param3Key,
                    paramName = deviceModel.value.trim(),
                    paramType = "Модель"
                )
                val param4 = Parameter(
                    id = param4Key,
                    paramName = deviceFault.value.trim(),
                    paramType = "Неисправность"
                )
                val paramList = mutableListOf(
                    param1,
                    param2,
                    param3,
                    param4
                )
                if (progressValue.value >= 1f) {
                    databaseRequests.child(pushKey).setValue(request)
                    if (!clientList.contains(client))
                        databaseClients.push().setValue(client)
                    val counter = mutableStateOf(0)
                    paramList.forEach { new ->
                        parameterList.forEach { old ->
                            if (new.paramName == old.paramName) {
                                keys[counter.value] = true
                            }
                        }
                        if (!keys[counter.value])
                            databaseParameters.child(new.id).setValue(new)
                        counter.value += 1
                    }
                    saveAndExit()
                }
            }
        }
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            scope.launch {
                sheetState.hide()
            }
        }
}