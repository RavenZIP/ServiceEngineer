package com.example.serviceengineer.screens.helpers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.customui.*
import com.example.serviceengineer.models.*
import com.example.serviceengineer.screens.main.SheetButton
import com.example.serviceengineer.screens.registerAndLogin.CustomButton
import com.example.serviceengineer.screens.work.ProgressBar
import com.example.serviceengineer.screens.work.RequestButton
import com.example.serviceengineer.screens.work.RequestItem
import com.example.serviceengineer.screens.work.checkKeys
import com.example.serviceengineer.ui.theme.*
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val buttonText = mutableStateOf("Далее")
val buttonIcon = mutableStateOf(Icons.Outlined.East)
val nextStep = mutableStateOf(false)
val stepState = mutableStateListOf<Boolean>()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TitleBottomSheet(
    scope: CoroutineScope
){
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(45.dp)
                .clip(RoundedCornerShape(15))
                .clickable(onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                }),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.Close,
                contentDescription = "close",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }

        Box(contentAlignment = Alignment.Center) {
            Text(
                text = modeModalLayout.value,
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp,
            )
        }
    }
}

@Composable
fun SearchFieldWithSettings(){
    Row(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 10.dp)
            .fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchField(0.85f)
        Box(
            modifier = Modifier
                .padding(top = 0.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(15))
                .clickable(onClick = { })
                .background(SecondColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Filter",
                tint = ThirdColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectRequest(){
    val scope = rememberCoroutineScope()
    val valuesList = remember { mutableListOf<Request>() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)
        LazyColumn(modifier = Modifier.padding(bottom = 10.dp)){
            items(requestList){request ->
                if (!request.finished)
                    if (!valuesList.contains(request)) {
                        valuesList.add(request)
                        RequestItem(request = request) {
                            for (i in 0..3)
                                checkKeys(i, 0.05f)
                            for (i in 4..5)
                                checkKeys(i, 0.1f)
                            for (i in 6..9)
                                checkKeys(i, 0.05f)
                            scope.launch {
                                sheetState.hide()
                            }
                        }
                    }
            }
        }
    }
}

@Composable
fun SaveAndExitOrExitOnly(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        SheetButton(scope = scope, "Закончить позднее", FirstButton, Icons.Outlined.ManageHistory)
        Spacer(modifier = Modifier.padding(top = 15.dp))
        SheetButton(scope = scope, "Выйти без сохранения", SecondButton, Icons.Outlined.ExitToApp)
        Spacer(modifier = Modifier.padding(top = 20.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDeviceType(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)

        val categories = mutableListOf<String>()

        parameterList.forEach {
            if (it.paramType == "Тип устройства")
                categories.add(it.paramName)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(categories) {
                CategoryButton(Text = it) {
                    checkKeys(0, 0.1f)
                    deviceType.value = it
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDeviceManufacturer(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)

        val categories = mutableListOf<String>()

        parameterList.forEach {
            if (it.paramType == "Производитель")
                categories.add(it.paramName)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(categories) {
                CategoryButton(Text = it) {
                    checkKeys(1, 0.1f)
                    deviceManufacturer.value = it
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDeviceModel(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)

        val categories = mutableListOf<String>()

        parameterList.forEach {
            if (it.paramType == "Модель")
                categories.add(it.paramName)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(categories) {
                CategoryButton(Text = it) {
                    checkKeys(2, 0.1f)
                    deviceModel.value = it
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDeviceFault(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)

        val categories = mutableListOf<String>()

        parameterList.forEach {
            if (it.paramType == "Неисправность")
                categories.add(it.paramName)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(categories) {
                CategoryButton(Text = it) {
                    if (modeModalLayout.value == "Заявленная неисправность") {
                        checkKeys(3, 0.1f)
                        deviceFault.value = it
                    } else {
                        checkKeys(11, 0.05f)
                        deviceFaultFinal.value = it
                    }
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectClient(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(0.9f)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        LazyColumn(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(bottom = 30.dp)){
            items(clientList) {
                Style2(
                    TitleText = it.clientSurname + " "
                            + it.clientName + " "
                            + it.clientPatronymic,
                    Text = it.clientPhone
                ) {
                    for (i in 6..9)
                        checkKeys(i, 0.1f)
                    clientName.value = it.clientName
                    clientSurname.value = it.clientSurname
                    clientPatronymic.value = it.clientPatronymic
                    clientPhone.value = it.clientPhone
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceAdd(
    database: DatabaseReference
){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
        ) {
            CustomOutlinedTextFieldSingleLine_v2(
                value = serviceName,
                text = "Наименование услуги"
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            CustomOutlinedTextFieldMultiLine_v2(
                value = serviceDescription,
                ImeAction = ImeAction.Next,
                minLines = 3,
                shapePercent = 10,
                text = "Описание"
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            CustomOutlinedTextFieldPrice(
                value = servicePrice,
                text = "Цена"
            )
        }
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = "Отмена",
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = activeText
                ) {
                    openSheet(scope = scope)
                    serviceName.value = ""
                    serviceDescription.value = ""
                    servicePrice.value = ""
                }
            }
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = mode.value,
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = ThirdColor
                ) {
                    if (mode.value == "Добавить") {
                        serviceKey.value = database.push().key.toString()
                        val service = Service(
                            serviceKey.value,
                            categoryName.value,
                            serviceName.value.trim(),
                            serviceDescription.value.trim(),
                            servicePrice.value.trim()
                        )
                        if (serviceName.value != "" && serviceDescription.value != "" && servicePrice.value != "")
                            database.child(serviceKey.value).setValue(service)
                    }
                    else {
                        val path = database.ref.child(serviceKey.value)
                        val data = mapOf<String, Any>(
                            "id" to serviceKey.value,
                            "category" to categoryName.value,
                            "service" to serviceName.value.trim(),
                            "description" to serviceDescription.value.trim(),
                            "price" to servicePrice.value.trim(),
                        )
                        if (serviceName.value != "" && serviceDescription.value != "" && servicePrice.value != "")
                            path.updateChildren(data)
                    }
                    openSheet(scope = scope)
                    serviceName.value = ""
                    serviceDescription.value = ""
                    servicePrice.value = ""
                    selectedServices.clear()
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun SparePartAdd(
    database: DatabaseReference
){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
        ) {
            CustomOutlinedTextFieldSingleLine_v2(
                value = sparePartName,
                text = "Наименование запчасти"
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            CustomOutlinedTextFieldMultiLine_v2(
                value = sparePartDescription,
                ImeAction = ImeAction.Next,
                minLines = 3,
                shapePercent = 10,
                text = "Описание"
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            CustomOutlinedTextFieldPrice(
                value = sparePartCountAvailable,
                text = "Количество"
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            CustomOutlinedTextFieldPrice(
                value = sparePartPrice,
                text = "Цена"
            )
        }
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = "Отмена",
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = activeText
                ) {
                    openSheet(scope = scope)
                    sparePartName.value = ""
                    sparePartDescription.value = ""
                    sparePartPrice.value = ""
                    sparePartCountAvailable.value = ""
                }
            }
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = mode.value,
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = ThirdColor
                ) {
                    if (mode.value == "Добавить") {
                        sparePartKey.value = database.push().key.toString()
                        val service = SparePart(
                            sparePartKey.value,
                            categoryName.value,
                            sparePartName.value.trim(),
                            sparePartDescription.value.trim(),
                            sparePartPrice.value.trim(),
                            sparePartCountAvailable.value.trim()
                        )
                        if (sparePartName.value != "" && sparePartDescription.value != "" && sparePartPrice.value != "")
                            database.child(sparePartKey.value).setValue(service)
                    }
                    else {
                        val path = database.ref.child(sparePartKey.value)
                        val data = mapOf<String, Any>(
                            "id" to sparePartKey.value,
                            "category" to categoryName.value,
                            "sparePart" to sparePartName.value.trim(),
                            "description" to sparePartDescription.value.trim(),
                            "price" to sparePartPrice.value.trim(),
                            "count" to sparePartCountAvailable.value.trim()
                        )
                        if (sparePartName.value != "" && sparePartDescription.value != "" && sparePartPrice.value != "")
                            path.updateChildren(data)
                    }
                    openSheet(scope = scope)
                    sparePartName.value = ""
                    sparePartDescription.value = ""
                    sparePartPrice.value = ""
                    sparePartCountAvailable.value = ""
                    selectedSpareParts.clear()
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun ParameterAdd(
    database: DatabaseReference
){
    val scope = rememberCoroutineScope()
    val key = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
        ) {
            CustomOutlinedTextFieldSingleLine_v2(
                value = paramName,
                text = "Наименование параметра"
            )
        }
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = "Отмена",
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = activeText
                ) {
                    openSheet(scope = scope)
                    paramName.value = ""
                }
            }
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = mode.value,
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = ThirdColor
                ) {
                    if (mode.value == "Добавить") {
                        paramKey.value = database.push().key.toString()
                        val param = Parameter(
                            paramKey.value,
                            paramName.value.trim(),
                            paramCategory.value
                        )
                        parameterList.forEach {
                            if (it.paramName == paramName.value) {
                                key.value = true
                            }
                        }
                        if (!key.value && paramName.value != "")
                            database.child(paramKey.value).setValue(param)
                        else {
                            openSheet(scope = scope)
                            paramName.value = ""
                        }
                    }
                    else {
                        val path = database.ref.child(paramKey.value)
                        val data = mapOf<String, Any>(
                            "id" to paramKey.value,
                            "paramName" to paramName.value.trim(),
                            "paramType" to paramCategory.value
                        )
                        if (paramName.value != "")
                            path.updateChildren(data)
                    }
                    openSheet(scope = scope)
                    paramName.value = ""
                    selectedParameters.clear()
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun EditSNP(
    database: DatabaseReference,
){
    val scope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val patronymic = remember { mutableStateOf("") }
    if (uName.value != "Нет данных")
        name.value = uName.value
    if (uSurname.value != "Нет данных")
        surname.value = uSurname.value
    if (uPatronymic.value != "Нет данных")
        patronymic.value = uPatronymic.value
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
        ) {
            CustomOutlinedTextFieldSingleLine_v2(
                value = surname ,
                text = "Фамилия"
            )
            CustomOutlinedTextFieldSingleLine_v2(
                value = name,
                text = "Имя"
            )
            CustomOutlinedTextFieldSingleLine_v2(
                value = patronymic,
                text = "Отчество"
            )
        }
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = "Отмена",
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = activeText
                ) {
                    openSheet(scope = scope)
                }
            }
            Box(modifier = Modifier.weight(1f)){
                CustomButton(
                    text = "Сохранить",
                    backgroundColor = SecondColor,
                    borderWidth = 0.dp,
                    borderColor = SecondColor,
                    contentColor = ThirdColor
                ) {
                    if (name.value != "")
                        uName.value = name.value
                    if (surname.value != "")
                        uSurname.value = surname.value
                    if (patronymic.value != "")
                        uPatronymic.value = patronymic.value
                    val path = database.ref
                    val data = mapOf<String, Any>(
                        "name" to uName.value,
                        "surname" to uSurname.value,
                        "patronymic" to uPatronymic.value
                    )
                    path.updateChildren(data)
                    openSheet(scope = scope)
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 20.dp))
}

@Composable
fun Organization(
    databaseCompany: DatabaseReference,
    databaseUserData: DatabaseReference
){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (mode.value == "") {
                UserJobCard(
                    title = "Создать новую организацию",
                    text = "Укажите название, добавляйте сотрудников и контролируйте их деятельность"
                ) {
                    CompanyName.value = ""
                    mode.value = "Значение 1"
                }
                Spacer(modifier = Modifier.padding(top = 15.dp))
                UserJobCard(
                    title = "Выбрать организацию",
                    text = "Отправьте запрос на вступление в организацию"
                ) {
                    mode.value = "Значение 2"
                }
            } else {
                if (mode.value == "Значение 1") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    ) {
                        CustomOutlinedTextFieldSingleLine_v2(
                            value = CompanyName,
                            text = "Наименование организации"
                        )
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        RequestButton(
                            text = "Загрузить изображение",
                            icon = Icons.Outlined.FileUpload,
                            left = true
                        ) {
                            
                        }
                        /*CustomOutlinedTextFieldSingleLine_v2(
                            value = CompanyMembersCount,
                            text = "Количество сотрудников"
                        )*/
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            CustomButton(
                                text = "Назад",
                                backgroundColor = SecondColor,
                                borderWidth = 0.dp,
                                borderColor = SecondColor,
                                contentColor = activeText
                            ) {
                                CompanyName.value = "Нет данных"
                                mode.value = ""
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            CustomButton(
                                text = "Создать",
                                backgroundColor = SecondColor,
                                borderWidth = 0.dp,
                                borderColor = SecondColor,
                                contentColor = ThirdColor
                            ) {
                                if (CompanyName.value != "") {
                                    val key = databaseCompany.push().key.toString()
                                    val company = Company(
                                        id = key,
                                        name = CompanyName.value,
                                        membersCount = "0",
                                        icon = ""
                                    )
                                    databaseCompany.child(key).child("Data").setValue(company)
                                    val path = databaseUserData.ref
                                    val data = mapOf<String, Any>(
                                        "job" to key,
                                        "post" to "Администратор"
                                    )
                                    path.updateChildren(data)
                                    openSheet(scope = scope)
                                }
                            }
                        }
                    }
                } else {
                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                        SearchField(1f)
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(bottom = 10.dp)
                        ) {
                            items(CompanyList) {
                                Style2(
                                    TitleText = it.name,
                                    Text = "Ничего нет"
                                ) {
                                    val key = databaseCompany.push().key.toString()
                                    val requestToCompany = RequestToCompany(
                                        id = key,
                                        userEmail = mAuth.currentUser?.email.toString(),
                                        validation = false
                                    )
                                    databaseCompany.child(it.id).child("Request").child(key)
                                        .setValue(requestToCompany)

                                    val path = databaseUserData.ref
                                    val data = mapOf<String, Any>(
                                        "job" to it.id,
                                        "post" to "Инженер (не подтверждено)"
                                    )
                                    path.updateChildren(data)

                                    openSheet(scope = scope)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        CustomButton(
                            text = "Назад",
                            backgroundColor = SecondColor,
                            borderWidth = 0.dp,
                            borderColor = SecondColor,
                            contentColor = ThirdColor
                        ) {
                            CompanyName.value = "Нет данных"
                            mode.value = ""
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(top = 20.dp))
}

@Composable
fun SelectServices(){
    val scope = rememberCoroutineScope()
    if (renderedServiceList.isNotEmpty() && !loadService){
        renderedServiceList.forEach { serviceId ->
            priceList.forEach { service ->
                if (service.id == serviceId) {
                    selectedServices.add(service)
                }
            }
        }
        loadService = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        SearchField(width = 0.9f)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.padding(
                    bottom = 15.dp,
                    top = 10.dp,
                    start = 5.dp,
                    end = 5.dp
                )
            ) {
                items(priceList) {
                    if (it.category == deviceType.value)
                        ServiceCard(
                            TitleText = it.service,
                            Text = it.description,
                            Price = it.price,
                            borderColor = if (selectedServices.contains(it)) ThirdColor else SecondColor
                        ) {
                            if (!selectedServices.contains(it)) {
                                selectedServices.add(it)
                            } else {
                                selectedServices.remove(it)
                            }
                        }
                }
            }
            Box(modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(0.9f)) {
                RequestButton(
                    text = "Завершить выбор",
                    icon = Icons.Outlined.Done,
                    false
                ) {
                    if (sum.value == "")
                        sum.value = "0"
                    renderedServiceList.clear()
                    for (item1 in priceList)
                        for (item2 in selectedServices)
                            if (item1 == item2) {
                                renderedServiceList.add(item1.id)
                                renderedServices.value += item1.id + ";"
                                sum.value = (sum.value.toInt() + item1.price.toInt()).toString()
                            }
                    if (renderedServiceList.isEmpty()) {
                        /*if (keys[12] == 1) {
                            if (spentSparePartList.isEmpty()) {
                                sum.value = ""
                                progressValue.value -= 0.05f
                                keys[14] = 0
                            }
                            renderedServicesStatus.value = ""
                            keys[12] = 0
                            progressValue.value -= 0.05f
                            renderedServices.value = ""
                        }*/
                        renderedServicesStatus.value = "Услуги не оказаны"
                        checkKeys(12, 0.05f)
                        checkKeys(14, 0.05f)
                        renderedServices.value = "Услуги не оказаны"
                    } else {
                        renderedServicesStatus.value =
                            "Выбрано " + renderedServiceList.count() + "..."
                        checkKeys(12, 0.05f)
                        checkKeys(14, 0.05f)
                        renderedServices.value = renderedServices.value.trim(';')
                    }
                    openSheet(scope = scope)
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SelectSpareParts(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        if (!nextStep.value) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            SearchField(0.9f)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!nextStep.value) {
                LazyColumn(
                    modifier = Modifier.padding(
                        bottom = 15.dp,
                        top = 10.dp,
                        start = 5.dp,
                        end = 5.dp
                    )
                ) {
                    items(sparePartsList) {
                        if (it.category == deviceType.value) {
                            SparePartCard(
                                TitleText = it.sparePart,
                                Text = it.description,
                                Price = it.price,
                                Count = it.count,
                                borderColor = if (selectedSpareParts.contains(it)) ThirdColor else SecondColor
                            ) {
                                if (!selectedSpareParts.contains(it)) {
                                    selectedSpareParts.add(it)
                                } else {
                                    selectedSpareParts.remove(it)
                                }
                            }
                        }
                    }
                    if (selectedSpareParts.isEmpty()) {
                        buttonText.value = "Завершить выбор"
                        buttonIcon.value = Icons.Outlined.Done
                    } else {
                        buttonText.value = "Далее"
                        buttonIcon.value = Icons.Outlined.East
                    }
                }
            } else {
                for (index in stepState.indices)
                    if (stepState[index]) {
                        Box(
                            Modifier
                                .fillMaxWidth(0.9f)
                                .padding(bottom = 20.dp)) {
                            CustomOutlinedTextFieldSingleLine_v2(
                                value = sparePartCountUsed,
                                text = selectedSpareParts[index].sparePart + ", шт."
                            )
                        }

                        if (index + 1 == stepState.count()) {
                            buttonText.value = "Завершить выбор"
                            buttonIcon.value = Icons.Outlined.Done
                        }
                    }
            }

            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)) {
                RequestButton(
                    text = buttonText.value,
                    icon = buttonIcon.value,
                    false
                ) {
                    if (selectedSpareParts.isNotEmpty() && !nextStep.value) {
                        stepState.clear()
                        sparePartCount.value = ""
                        nextStep.value = true
                        for (index in selectedSpareParts.indices)
                            if (index == 0)
                                stepState.add(true)
                            else
                                stepState.add(false)
                    }
                    else {
/*                        if(keys[13] == 1) {
                            if (renderedServiceList.isEmpty()) {
                                sum.value = ""
                                progressValue.value -= 0.05f
                                keys[14] = 0
                            }
                        }*/
                        checkKeys(13, 0.05f)
                        checkKeys(14, 0.05f)
                        spareParts.value = "Запчасти не использовались"
                        sparePartsStatus.value = "Запчасти не использовались"
                        openSheet(scope = scope)
                    }
                    if (nextStep.value && sparePartCountUsed.value.trim().isNotEmpty()){
                        for (index in stepState.indices) {
                            if (stepState[index]) {
                                sparePartCount.value += sparePartCountUsed.value + ";"
                                sparePartCountUsed.value = ""
                                stepState[index] = false
                                if (index + 1 < stepState.count())
                                    stepState[index + 1] = true
                                else if (index + 1 == stepState.count() && !stepState[index]) {
                                    if (sum.value == "")
                                        sum.value = "0"
                                    spentSparePartList.clear()
                                    spareParts.value = ""
                                    sparePartsStatus.value = ""
                                    val selectedSparePartsPrice = sparePartCount.value.split(';').toList()
                                    for (item1 in sparePartsList)
                                        for (item2 in selectedSpareParts)
                                            if (item1 == item2) {
                                                spentSparePartList.add(item1.id)
                                                spareParts.value += item1.id + ";"
                                                sum.value = (sum.value.toInt() + item1.price.toInt() * selectedSparePartsPrice[selectedSpareParts.indexOf(item2)].toInt()).toString()
                                            }
                                    sparePartsStatus.value =
                                        "Выбрано " + spentSparePartList.count() + "..."
                                    checkKeys(13, 0.05f)
                                    spareParts.value = spareParts.value.trim(';')
                                    sparePartCount.value = sparePartCount.value.trim(';')
                                    nextStep.value = false
                                    openSheet(scope = scope)
                                }
                                break
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetImageInfo(){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
        ) {
            if (!uploadImage.value) {
                CustomOutlinedTextFieldSingleLine_v2(
                    value = schemeName,
                    text = "Наименование схемы"
                )
                CustomOutlinedTextFieldSingleLine_v2(
                    value = fileName,
                    text = "Наименование файла"
                )
            } else
                ProgressBar()
        }
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            if (!uploadImage.value) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomButton(
                        text = "Отмена",
                        backgroundColor = SecondColor,
                        borderWidth = 0.dp,
                        borderColor = SecondColor,
                        contentColor = activeText
                    ) {
                        openSheet(scope = scope)
                        schemeName.value = "-"
                        fileName.value = ""
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    CustomButton(
                        text = "Сохранить",
                        backgroundColor = SecondColor,
                        borderWidth = 0.dp,
                        borderColor = SecondColor,
                        contentColor = ThirdColor
                    ) {
                        if (schemeName.value != "" && fileName.value != "")
                            uploadImage.value = true
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
fun openSheet(
    scope: CoroutineScope
){
    scope.launch {
        if(!sheetState.isVisible) {
            delay(50)
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }
}

