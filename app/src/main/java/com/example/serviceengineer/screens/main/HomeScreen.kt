package com.example.serviceengineer.screens.main

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviceengineer.customui.*
import com.example.serviceengineer.localDatabase.ReportViewModel
import com.example.serviceengineer.localDatabase.ReportViewModelFactory
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@Composable
fun Home(){
    TitleAppBar.value = "Главная"
    Column(modifier = Modifier
        .padding(bottom = 30.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.padding(top = 10.dp))

        var finished = 0
        var averageValue = 0L
        var averageMinute = 0
        var money = 0
        var averageMoney = 0
        val percentFault = 87

        val reportViewModel: ReportViewModel = viewModel(
            factory = ReportViewModelFactory(LocalContext.current.applicationContext as Application)
        )
        val localReportsList = reportViewModel.readAllData.observeAsState(listOf()).value

        val requestChartValues = mutableListOf<Float>()
        val reportChartValues = mutableListOf<Float>()
        val deviceTypeChartValues = mutableListOf<Int>()
        val deviceFaultFinalList = mutableListOf<Float>()
        val averageTimeWork = mutableListOf<Long>()

        val legend = listOf("Выполнено", "Активные")
        val legend2 = listOf("Завершено", "Активные")
        val legend3 = mutableListOf<String>()
        val legend4 = mutableListOf<String>()

        if (parameterList.isNotEmpty()) {
            parameterList.forEach {
                if (it.paramType == "Тип устройства")
                    legend3.add(it.paramName)
            }
        }

        if (requestList.isNotEmpty()) {
            requestList.forEach {
                if (it.finished)
                    finished++
            }
            requestChartValues.add(finished.toFloat())
            requestChartValues.add((requestList.count() - finished).toFloat())
            if (legend3.isNotEmpty()) {
                val legend3_f = mutableListOf<String>()
                finished = 0
                legend3.forEach { legend ->
                    requestList.forEach {
                        if (legend == it.deviceType)
                            finished++
                    }
                    if (finished != 0) {
                        deviceTypeChartValues.add(finished)
                        finished = 0
                        legend3_f.add(legend)
                    }
                }
                legend3.clear()
                legend3_f.forEach {
                    legend3.add(it)
                }
            }
        }

        if (reportsList.isNotEmpty()) {
            money = 0
            reportChartValues.add(reportsList.count().toFloat())
            reportChartValues.add(localReportsList.count().toFloat())
            reportsList.forEach { report ->
                requestList.forEach {
                    if (report.requestId == it.id) {
                        if (!legend4.contains(report.deviceFaultFinal) && it.deviceType == legend3[deviceTypeChartValues.indexOf(deviceTypeChartValues.max())])
                            legend4.add(report.deviceFaultFinal)
                    }
                }
                money += report.sum.toInt()
            }
            averageMoney = money / reportsList.count()
        }

        if (legend4.isNotEmpty()) {
            finished = 0
            legend4.forEach { legend ->
                reportsList.forEach { report ->
                    requestList.forEach {
                        if (report.requestId == it.id) {
                            if (legend == report.deviceFaultFinal && it.deviceType == legend3[deviceTypeChartValues.indexOf(deviceTypeChartValues.max())])
                                finished++
                        }
                    }
                }
                if (finished != 0) {
                    deviceFaultFinalList.add(finished.toFloat())
                    finished = 0
                }
            }
        }

        if (requestList.isNotEmpty() && reportsList.isNotEmpty()){
            val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm")
            requestList.forEach { request ->
                reportsList.forEach { report ->
                    if (report.requestId == request.id) {
                        averageTimeWork.add(getDateDifferenceInDays(
                            formatter.parse(request.requestDate), formatter.parse(report.reportDate)
                        ))
                    }
                }
            }
        }

        if (averageTimeWork.isNotEmpty()){
            averageValue = 0L
            averageTimeWork.forEach {
                averageValue += it
            }
            averageValue /= averageTimeWork.count()
            for (i in 0..1)
                if (averageValue.length() > 2) {
                    averageValue /= 60
                    averageMinute += 1
                }
                else break
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.End),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(Modifier.weight(1f)) {
                ChartWithTitle(
                    "Зявки",
                    requestChartValues,
                    legend,
                    100,
                    14,
                    10.dp,
                    true,
                    ""
                ) {

                }
            }

            Box(Modifier.weight(1f)) {
                ChartWithTitle(
                    "Отчеты",
                    reportChartValues,
                    legend2,
                    100,
                    14,
                    10.dp,
                    true,
                    ""
                ) {

                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Statistic("Часто ремонтируемые устройства", deviceTypeChartValues, legend3){

        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.End),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(Modifier.weight(1f)) {
                Static_v2(TitleText = "Обслужено \nустройств", reportsList.count().toString(), "") {

                }
            }

            Box(Modifier.weight(1f)) {
                Static_v2(TitleText = "Ср. время ремонта", (averageValue).toString(), if (averageMinute == 2) "ч." else if (averageMinute == 1) "мин." else if (averageMinute == 0 && averageValue == 0L) "" else "сек.") {

                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        ChartWithTitle(
            TitleText = "Наиболее частые неисправности",
            values = deviceFaultFinalList,
            legend = legend4,
            size = 100,
            fontSize = 14,
            size2 = 10.dp,
            rect = false,
            deviceType = if (deviceTypeChartValues.isNotEmpty()) legend3[deviceTypeChartValues.indexOf(deviceTypeChartValues.max())] else "нет данных..."
        ) {

        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Static_v2(TitleText = "Совпадение неисправностей", percentFault.toString(), "%") {

        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Static_v2(TitleText = "Заработок за месяц", money.toString(), "руб.") {

        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Static_v2(TitleText = "Средняя стоимость ремонта", averageMoney.toString(), "руб.") {

        }
        /*Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.End),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(Modifier.weight(1f)) {
                Static_v2(TitleText = "Заработок за месяц", "1000", "руб.") {

                }
            }

            Box(Modifier.weight(1f)) {
                Static_v2(TitleText = "Совпадение неисправностей", "89", "%") {

                }
            }
        }*/

        Spacer(modifier = Modifier.padding(bottom = 65.dp))
    }
}

private fun Long.length() = when(this) {
    0L -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

private fun getDateDifferenceInDays(date1: Date, date2: Date) : Long {

    // Get the date in milliseconds
    val millis1: Long = date1.time
    val millis2: Long = date2.time


    // Calculate difference in milliseconds
    val diff = millis2 - millis1

    val seconds = diff / 1000
    val minutes = diff / (60 * 1000)
    val hours = minutes / 60

    // Calculate difference in days
    val diffDays = diff / (24 * 60 * 60 * 1000)

    return seconds

/*    val report_date = Calendar.getInstance()
    val request_date = Calendar.getInstance()
    report_date.timeInMillis = formatter.parse(report.reportDate).time
    request_date.timeInMillis = formatter.parse(request.requestDate).time
    Log.d("report1", report_date.time.toString())
    Log.d("request1", request_date.time.toString())

    val test2 = (report_date.time.time - request_date.time.time) / 1000
    Log.d("final2", test2.toString())*/

/*    val from = LocalDate.parse(report.reportDate, DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"))
    val from2 = LocalDate.parse(request.requestDate, DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"))
    var period = Period.between(from, from2)*/
}

/** Часто ремонтируемые устройства **/
@Composable
private fun Statistic(
    TitleText: String,
    values: List<Int>,
    deviceName: List<String>,
    onClick: () -> Unit
){
    TextButton(
        onClick = { onClick() },
        Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = TitleText,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "",
                    Modifier.size(25.dp)
                )
            }
            Spacer(modifier = Modifier.height(if (values.isNotEmpty()) 25.dp else 10.dp))
            if (values.isNotEmpty()) {
                for (index in values.indices) {
                    TwoTextOneLine(
                        deviceName[index] + " (" + values[index] + ")",
                        (values[index].toFloat() * 100 / requestList.count()).roundToInt().toString()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

            } else
                Text(text = "Нет данных для составления статистики...", letterSpacing = 0.5.sp)
        }
    }
}

@Composable
private fun TwoTextOneLine(deviceName: String, percent: String){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = deviceName,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp
        )

        Text(
            text = "$percent%",
            fontSize = 14.sp,
            color = ThirdColor,
            fontWeight = FontWeight.W400,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun Static_v2(
    TitleText: String,
    Number: String,
    Text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = TitleText,
                    fontSize =
                    if (TitleText == "Заработок за месяц" ||
                        TitleText == "Совпадение неисправностей" ||
                        TitleText == "Средняя стоимость ремонта"
                    )
                        20.sp
                    else
                        16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "",
                    Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.padding(top = 15.dp))

            Row() {
                Text(
                    text = Number,
                    color = ThirdColor.copy(0.8f),
                    fontSize = 50.sp
                )

                if (TitleText == "Ср. время ремонта" ||
                    TitleText == "Заработок за месяц" ||
                    TitleText == "Совпадение неисправностей" ||
                    TitleText == "Средняя стоимость ремонта")
                    Text(
                        text = Text,
                        color = ThirdColor.copy(0.8f),
                        fontSize =
                        if (TitleText == "Заработок за месяц" ||
                            TitleText == "Совпадение неисправностей" ||
                            TitleText == "Средняя стоимость ремонта"
                        )
                            24.sp
                        else
                            18.sp,
                        modifier = Modifier.align(Alignment.Bottom).padding(bottom = 8.dp)
                    )
            }


            Spacer(modifier = Modifier.padding(top = 5.dp))
        }
    }
}
