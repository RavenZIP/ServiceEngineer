package com.example.serviceengineer.screens.work

import android.annotation.SuppressLint
import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviceengineer.customui.CustomOutlinedTextFieldMultiLine
import com.example.serviceengineer.customui.CustomOutlinedTextFieldNumbers
import com.example.serviceengineer.customui.CustomOutlinedTextFieldSingleLine
import com.example.serviceengineer.models.LocalReport
import com.example.serviceengineer.models.Parameter
import com.example.serviceengineer.models.Report
import com.example.serviceengineer.localDatabase.ReportViewModel
import com.example.serviceengineer.localDatabase.ReportViewModelFactory
import com.example.serviceengineer.screens.helpers.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private lateinit var databaseReports: DatabaseReference
private lateinit var databaseRequests: DatabaseReference
private lateinit var databaseParameters: DatabaseReference

@Composable
private fun TopBarButton(
    exit:() -> Unit
){
    val scope = rememberCoroutineScope()
    val reportViewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    if (mode.value == "add")
        localReportId.value = reportViewModel.readAllData.observeAsState(listOf()).value.indices.last + 1
    onClickTopBarButton.value = {
        modeModalLayout.value = "Выход"
        FirstButton.value = {
            saveLocalData(reportViewModel)
            exit()
        }
        SecondButton.value = {
            exit()
        }
        openSheet(scope = scope)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddReport(
    step2Click:() -> Unit,
    exit:() -> Unit
){
    modeModalLayout.value = "Заявка"
    TitleAppBar.value = "Создание отчета"
    val scope = rememberCoroutineScope()
    TopBarButton(exit)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()
            CustomOutlinedTextFieldSingleLine(
                value = deviceType,
                text = "Тип устройства",
                weight = 0.05f,
                id = 0,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldSingleLine(
                value = deviceManufacturer,
                text = "Производитель/Бренд",
                weight = 0.05f,
                id = 1,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldSingleLine(
                value = deviceModel,
                text = "Модель",
                weight = 0.05f,
                id = 2,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldSingleLine(
                value = deviceFault,
                text = "Заявленная неисправность",
                weight = 0.05f,
                id = 3,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldMultiLine(
                value = deviceKit,
                text = "Комплектация",
                minLines = 3,
                ImeAction = ImeAction.Next,
                weight = 0.1f,
                id = 4,
                shapePercent = 10,
                enabled = false
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
                enabled = false
            )
        }
        RequestButton (
            text = "Следующий этап",
            icon = Icons.Outlined.East,
            false
        ) {
            step2Click()
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            exit()
            scope.launch {
                sheetState.hide()
            }
        }
    if (!sheetState.isVisible && deviceType.value == "")
        exit()
}

@Composable
fun AddReportStep2(
    step3Click:() -> Unit,
    exit:() -> Unit
){
    TopBarButton(exit)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()
            CustomOutlinedTextFieldSingleLine(
                value = clientSurname,
                text = "Фамилия",
                weight = 0.05f,
                id = 6,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientName,
                text = "Имя",
                weight = 0.05f,
                id = 7,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientPatronymic,
                text = "Отчество",
                weight = 0.05f,
                id = 8,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            CustomOutlinedTextFieldNumbers(
                value = clientPhone,
                text = "Номер телефона",
                weight = 0.05f,
                id = 9,
                enabled = false
            )
        }
        RequestButton (
            text = "Следующий этап",
            icon = Icons.Outlined.East,
            false
        ) {
            step3Click()
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@Composable
fun AddReportStep3(
    step4Click:() -> Unit,
    exit:() -> Unit
){
    TopBarButton(exit)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()
            CustomOutlinedTextFieldMultiLine(
                value = reportText,
                text = "Текст отчета",
                minLines = 15,
                ImeAction = ImeAction.Done,
                weight = 0.2f,
                id = 10,
                shapePercent = 5,
                enabled = true
            )
        }
        RequestButton (
            text = "Следующий этап",
            icon = Icons.Outlined.East,
            false
        ){
            step4Click()
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddReportStep4(
    saveAndExit:() -> Unit,
    exit:() -> Unit
){
    TopBarButton(exit)
    val reportDate = SimpleDateFormat(
        "dd MMMM yyyy, HH:mm", Locale.getDefault()
    )
    databaseRequests = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Requests")
    databaseReports = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Reports")
    databaseParameters = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Parameters")
    val scope = rememberCoroutineScope()
    val reportViewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()
            Option(
                value = deviceFaultFinal,
                text = "Обнаруженные неисправности",
                weight = 0.05f,
                id = 11,
                enabled = true
            ) {
                modeModalLayout.value = "Обнаруженная неисправность"
                openSheet(scope = scope)
            }
            Option(
                value = renderedServicesStatus,
                text = "Оказанные услуги",
                weight = 0.05f,
                id = 12,
                enabled = false
            ) {
                modeModalLayout.value = "Оказанные услуги"
                openSheet(scope = scope)
            }
            Option(
                value = sparePartsStatus,
                text = "Запчасти и принадлежности",
                weight = 0.05f,
                id = 13,
                enabled = false
            ) {
                modeModalLayout.value = "Потраченные запчасти"
                openSheet(scope = scope)
            }
            CustomOutlinedTextFieldNumbers(
                value = sum,
                text = "Итоговая сумма ремонта, руб.",
                weight = 0.05f,
                id = 14,
                enabled = false
            )
        }
        RequestButton (
            text = "Завершить",
            icon = Icons.Outlined.Done,
            false
        ){
            val pushKey = databaseReports.push().key.toString()
            val paramKey = databaseRequests.push().key.toString()

            val report = Report(
                id = pushKey,
                requestId = requestId.value,
                reportText = reportText.value,
                deviceFaultFinal = deviceFaultFinal.value,
                renderedServicesId = renderedServices.value,
                sparePartsId = spareParts.value,
                sparePartsCount = sparePartCount.value,
                sum = sum.value,
                reportDate = reportDate.format(Date())
            )
            val path = databaseRequests.ref.child(requestId.value)  //Для смены статуса "Завершено" у заявки
            val data = mapOf(
                //"id" to requestId.value,
                "finished" to true,
/*                "deviceType" to deviceType.value,
                "deviceManufacturer" to deviceManufacturer.value,
                "deviceModel" to deviceModel.value,
                "deviceFault" to deviceFault.value,
                "deviceKit" to deviceKit.value,
                "deviceAppearance" to deviceAppearance.value,
                "clientPhone" to clientPhone.value*/
            )
            if (mode.value == "update") {
                val localReport = LocalReport(
                    localReportId.value,
                    requestId.value,
                    reportText.value,
                    deviceFaultFinal.value,
                    renderedServices.value,
                    spareParts.value,
                    sparePartCount.value,
                    sum.value
                )
                reportViewModel.deleteTodo(localReport)
            }
            var key = false
            val param = Parameter(
                id = paramKey,
                paramName = deviceFaultFinal.value.trim(),
                paramType = "Неисправность"
            )
            if (progressValue.value >= 1f) {
                databaseReports.child(pushKey).setValue(report)

                parameterList.forEach {
                    if (it.paramName == param.paramName) {
                        key = true
                    }
                }

                if (!key)
                    databaseParameters.child(param.id).setValue(param)

                path.updateChildren(data)
                saveAndExit()
            }
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

private fun saveLocalData(reportViewModel: ReportViewModel){
    val localReport = LocalReport(
        localReportId.value,
        requestId.value,
        reportText.value,
        deviceFaultFinal.value,
        renderedServices.value,
        spareParts.value,
        sparePartCount.value,
        sum.value
    )
    if (mode.value == "add")
        reportViewModel.addTodo(localReport)
    else
        reportViewModel.updateTodo(localReport)
}
