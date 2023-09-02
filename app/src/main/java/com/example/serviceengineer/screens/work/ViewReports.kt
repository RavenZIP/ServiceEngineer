package com.example.serviceengineer.screens.work

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.models.LocalReport
import com.example.serviceengineer.models.Report
import com.example.serviceengineer.models.Request
import com.example.serviceengineer.models.SparePart
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.main.HorizontalPagerInfoWithTabs
import com.example.serviceengineer.screens.main.HorizontalPagerReportList
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import kotlin.reflect.typeOf

@Composable
fun ViewReports(
    viewReport:() -> Unit,
    updateReport:() -> Unit
){
    TitleAppBar.value = "Отчеты"
    Column(Modifier.fillMaxSize()) {
        HorizontalPagerReportList(
            viewReport,
            updateReport
        )
    }
}

@Composable
fun ViewReport(){
    TitleAppBar.value = "Отчет"
    Column(Modifier.fillMaxSize()) {
        HorizontalPagerInfoWithTabs(count = 4)
    }
}

@Composable
fun ReportItem(
    request: Request,
    report: Any,
    onClick:() -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(15))
            .clickable {
                if (report is Report) {
                    mode.value = "add"
                    requestList.forEach {
                        if (it.id == report.requestId) {
                            requestId.value = it.id
                            deviceType.value = it.deviceType
                            deviceManufacturer.value = it.deviceManufacturer
                            deviceModel.value = it.deviceModel
                            deviceFault.value = it.deviceFault
                            deviceKit.value = it.deviceKit
                            deviceAppearance.value = it.deviceAppearance
                            //clientPhone.value = it.clientPhone
                        }
                    }
                    reportText.value = report.reportText
                    deviceFaultFinal.value = report.deviceFaultFinal
                    renderedServiceList = report.renderedServicesId
                        .split(';')
                        .toMutableList()
                    spentSparePartList = report.sparePartsId
                        .split(';')
                        .toMutableList()
                    spentSparePartCount = report.sparePartsCount
                        .split(';')
                        .toMutableList()

                    if (renderedServiceList[0] != "")
                        renderedServicesStatus.value =
                            "Выбрано " + renderedServiceList.count() + "..."
                    if (spentSparePartList[0] != "")
                        sparePartsStatus.value = "Выбрано " + spentSparePartList.count() + "..."

                    sum.value = report.sum
                    reportDate.value = report.reportDate
                } else if (report is LocalReport) {
                    mode.value = "update"
                    requestList.forEach {
                        if (it.id == report.requestId) {
                            requestId.value = it.id
                            deviceType.value = it.deviceType
                            deviceManufacturer.value = it.deviceManufacturer
                            deviceModel.value = it.deviceModel
                            deviceFault.value = it.deviceFault
                            deviceKit.value = it.deviceKit
                            deviceAppearance.value = it.deviceAppearance
                            //clientPhone.value = it.clientPhone
                        }
                    }

                    localReportId.value = report.id!!
                    reportText.value = report.reportText
                    deviceFaultFinal.value = report.deviceFaultFinal
                    renderedServiceList = report.renderedServices
                        .split(';')
                        .toMutableList()
                    spentSparePartList = report.spareParts
                        .split(';')
                        .toMutableList()

                    sparePartCount.value = report.sparePartsCount
                    spareParts.value = report.spareParts
                    renderedServices.value = report.renderedServices

                    if (renderedServiceList[0] != "") {
                        renderedServicesStatus.value =
                            "Выбрано " + renderedServiceList.count() + "..."
                        checkKeys(12, 0.05f)
                    }
                    if (spentSparePartList[0] != "") {
                        sparePartsStatus.value = "Выбрано " + spentSparePartList.count() + "..."
                        checkKeys(13, 0.05f)
                    }

                    sum.value = report.sum
                    reportDate.value = ""
                    loadService = false

                    for (i in 0..3)
                        checkKeys(i, 0.05f)
                    for (i in 4..5)
                        checkKeys(i, 0.1f)
                    for (i in 6..9)
                        checkKeys(i, 0.05f)
                    if (reportText.value != "")
                        checkKeys(10, 0.2f)
                    if (deviceFaultFinal.value != "")
                        checkKeys(11, 0.05f)
                    if (sum.value != "")
                        checkKeys(14, 0.05f)
                }
                clientList.forEach {
                    if (it.clientPhone == clientPhone.value) {
                        clientSurname.value = it.clientSurname
                        clientName.value = it.clientName
                        clientPatronymic.value = it.clientPatronymic
                    }
                }
                onClick()
            },
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(15),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = request.deviceManufacturer + " " + request.deviceModel,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = ThirdColor.copy(0.8f)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Тип устройства",
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = request.deviceType,
                fontSize = 14.sp,
                letterSpacing = 0.3.sp
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = "Заявленная неисправность",
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = request.deviceFault,
                fontSize = 14.sp,
                letterSpacing = 0.3.sp
            )
        }
    }
}