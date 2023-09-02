package com.example.serviceengineer.screens.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviceengineer.models.Request
import com.example.serviceengineer.localDatabase.ReportViewModel
import com.example.serviceengineer.localDatabase.ReportViewModelFactory
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.work.ReportItem
import com.example.serviceengineer.screens.work.RequestItem
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.activeText
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomTabRow(
    pagerState: PagerState,
    TabPage: List<String>
){
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = ThirdColor
            )
        },
        backgroundColor = MainColor,
        divider = { Divider(color = ThirdColor.copy(0.5f)) },
    ) {
        TabPage.forEachIndexed { index, title ->
            Tab(
                enabled = false,
                selected = pagerState.currentPage == index,
                onClick = { },
                selectedContentColor = ThirdColor,
                unselectedContentColor = ThirdColor.copy(0.5f)
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(top = 10.dp),
                    text = title,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
/** Карточка с двумя текстовыми элементами. **/
@Composable
fun DataCard_v1(title: String, text: String, shape: Int){
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(shape)),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(shape),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = ThirdColor.copy(0.8f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (title == "Итоговая сумма ремонта") "$text руб." else text,
                color = activeText,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

/** Карточка с двумя текстовыми элементами и выпадающим списком. **/
@SuppressLint("UnrememberedMutableState")
@Composable
fun DataCard_v2(title: String, text: MutableList<String>, pricelist: Boolean){
    val opened = mutableStateOf(false)
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15))
        .clickable {
            opened.value = !opened.value
        },
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(15),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = ThirdColor.copy(0.8f),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Icon(
                    if (opened.value) Icons.Outlined.ExpandMore else Icons.Outlined.ChevronRight,
                    contentDescription = "",
                    tint = ThirdColor.copy(0.8f),
                    modifier = Modifier.weight(1f)
                )
            }
            if (opened.value) {
                for (item in text.indices) {
                    if (pricelist)
                        priceList.forEach { pricelist ->
                            if (pricelist.id == text[item]) {
                                Row(modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = pricelist.service,
                                        color = activeText,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = pricelist.price + " руб.",
                                        color = activeText,
                                        modifier = Modifier.fillMaxWidth(0.25f)
                                    )
                                }
                            }
                        }
                    else
                        sparePartsList.forEach { sparePart ->
                            if (sparePart.id == text[item])
                                Row(modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = sparePart.sparePart + ", " + spentSparePartCount[item] + " шт.",
                                        color = activeText,
                                        modifier = Modifier.fillMaxWidth(0.5f)
                                    )
                                    Text(
                                        text = (spentSparePartCount[item].toInt() * sparePart.price.toInt()).toString() + " руб.",
                                        color = activeText,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.End
                                    )
                                }
                        }
                }
            }
        }
    }
}

@Composable
fun DeviceData(){
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Тип устройства", deviceType.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Производитель", deviceManufacturer.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Модель", deviceModel.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Заявленная неисправность", deviceFault.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Комплектация", deviceKit.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Внешний вид", deviceAppearance.value, 15)
    Spacer(modifier = Modifier.padding(top = 20.dp))
}

@Composable
fun ClientData(){
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Фамилия", clientSurname.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Имя", clientName.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Отчество", clientPatronymic.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Номер телефона", clientPhone.value, 15)
    Spacer(modifier = Modifier.padding(top = 20.dp))
}

@Composable
fun ResultData(){
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Обнаруженные неисправности", deviceFaultFinal.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v2("Оказанные услуги", renderedServiceList, true)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v2("Запчасти и принадлежности", spentSparePartList, false)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Итоговая сумма ремонта", sum.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Дата составления отчета", reportDate.value, 15)
    Spacer(modifier = Modifier.padding(top = 20.dp))
}

@Composable
fun OtherData(){
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Статус заявки", requestStatus.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
    DataCard_v1("Дата составления заявки", requestDate.value, 15)
    Spacer(modifier = Modifier.padding(top = 15.dp))
/*    DataCard_v1("Дата закрытия заявки", "Временно не работает")
    Spacer(modifier = Modifier.padding(top = 20.dp))*/
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerRequestList(
    onClick:() -> Unit
) {
    val pagerState = rememberPagerState()
    val tabPage = mutableListOf(
        "Активные",
        "Выполненные"
    )
    CustomTabRow(pagerState = pagerState, TabPage = tabPage)
    HorizontalPager(
        count = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    )
    { currentPage ->
        when (currentPage) {
            0 -> ActiveRequestList(onClick)
            1 -> ClosedRequestList(onClick)
        }
    }
}

@Composable
fun ActiveRequestList(
    onClick:() -> Unit
){
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
    ) {
        items(requestList) { request ->
            if (!request.finished)
                RequestItem(request = request, onClick = onClick)
        }
    }
}

@Composable
fun ClosedRequestList(
    onClick:() -> Unit
){
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
    ) {
        items(requestList) { request ->
            if (request.finished)
                RequestItem(request = request, onClick)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerReportList(
    onClick:() -> Unit,
    onClick2:() -> Unit
) {
    val pagerState = rememberPagerState()
    val tabPage = mutableListOf(
        "Серверные",
        "Локальные"
    )
    CustomTabRow(pagerState = pagerState, TabPage = tabPage)
    HorizontalPager(
        count = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { currentPage ->
        when (currentPage) {
            0 -> ServerReportList(onClick)
            1 -> LocalReportList(onClick2)
        }
    }
}

@Composable
fun ServerReportList(
    onClick:() -> Unit
){
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
    ) {
        var request = Request()
        items(reportsList) { report ->
            requestList.forEach {
                if (it.id == report.requestId)
                    request = it
            }
            ReportItem(
                report = report,
                request = request,
                onClick = onClick
            )
        }
    }
}

@Composable
fun LocalReportList(
    onClick:() -> Unit
) {
    val reportViewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val localReportsList = reportViewModel.readAllData.observeAsState(listOf()).value

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
    ) {
        var request = Request()
        items(localReportsList) { report ->
            requestList.forEach {
                if (it.id == report.requestId)
                    request = it
            }
            ReportItem(
                report = report,
                request = request,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerInfoWithTabs(
    count: Int
){
    val pagerState = rememberPagerState()
    val tabPage = mutableListOf("Устройство", "Клиент")
    if (count == 3) {
        tabPage.remove("Отчет")
        tabPage.remove("Итоги")
        tabPage.add("Прочее")
    }
    else{
        tabPage[0] = "Устр-во"
        tabPage.add("Отчет")
        tabPage.add("Итоги")
    }
    CustomTabRow(pagerState = pagerState, TabPage = tabPage)
    HorizontalPager(
        count = count,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    )
    {currentPage ->
        when (currentPage) {
            0 ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    DeviceData()
                }
            1 ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    ClientData()
                }
            2 ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    if (count == 3)
                        OtherData()
                    else {
                        Spacer(modifier = Modifier.padding(top = 15.dp))
                        DataCard_v1("Текст отчета", reportText.value, 5)
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                    }
                }
            3 ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    ResultData()
                }
        }

    }
}