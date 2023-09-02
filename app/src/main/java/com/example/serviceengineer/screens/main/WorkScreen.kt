package com.example.serviceengineer.screens.main

import android.app.Application
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.serviceengineer.localDatabase.ReportViewModel
import com.example.serviceengineer.localDatabase.ReportViewModelFactory
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.work.*
import com.example.serviceengineer.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

private val AddButtonText = mutableStateOf("")
private val ViewButtonText = mutableStateOf("")

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Work(
    addRequest: () -> Unit,
    addReport: () -> Unit,
    addNote: () -> Unit,
    viewRequests: () -> Unit,
    viewReports: () -> Unit,
    viewNotes: () -> Unit,
    viewPriceList: () -> Unit,
    viewSparePartsList: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    clearData()
    TitleAppBar.value = "Ремонт"
    modeModalLayout.value = "Ремонт"

    val requestButton = Category(
        TextTitle = "Заявки",
        objectCount = requestList.count().toString(),
        Icon = Icons.Outlined.Summarize
    ){
        AddButtonText.value = "Создать заявку"
        ViewButtonText.value = "Посмотреть заявки"
        FirstButton.value = {
            addRequest()
            progressValue.value = 0f
            keys.forEach { _ ->
                val target = keys.indexOf(1)
                if (target != -1)
                    keys[target] = 0
            }
        }
        SecondButton.value = { viewRequests() }
    }
    val context = LocalContext.current
    val reportViewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(context.applicationContext as Application)
    )
    val localReportsList = reportViewModel.readAllData.observeAsState(listOf()).value
    val reportButton = Category(
        TextTitle = "Отчеты",
        objectCount = (reportsList.count() + localReportsList.count()).toString(),
        Icon = Icons.Outlined.FactCheck
    ){
        AddButtonText.value = "Создать отчет"
        ViewButtonText.value = "Посмотреть отчеты"
        FirstButton.value = {
            mode.value = "add"
            addReport()
            progressValue.value = 0f
            keys.forEach { _ ->
                val target = keys.indexOf(1)
                if (target != -1)
                    keys[target] = 0
            }
        }
        SecondButton.value = { viewReports() }
    }
    val priceListButton = Category(
        TextTitle = "Прайс-лист",
        objectCount = priceList.count().toString(),
        Icon = Icons.Outlined.RequestQuote
    ){
        viewPriceList()
    }
    val noteButton = Category(
        TextTitle = "Заметки",
        objectCount = noteList.count().toString(),
        Icon = Icons.Outlined.ReceiptLong
    ){
        AddButtonText.value = "Создать заметку"
        ViewButtonText.value = "Посмотреть заметки"
        FirstButton.value = {
            NoteMode.value = 0
            addNote()
        }
        SecondButton.value = {
            NoteMode.value = 1
            viewNotes()
        }
    }
    val sparePartsButton = Category(
        TextTitle = "Запчасти",
        objectCount = sparePartsList.count().toString(),
        Icon = Icons.Outlined.PrecisionManufacturing
    ){
        viewSparePartsList()
    }
    val documentsButton = Category(
        TextTitle = "Документы",
        objectCount = "0",
        Icon = Icons.Outlined.Description
    ){

    }
    val buttonList = mutableListOf(
        requestButton,
        reportButton,
        priceListButton,
        sparePartsButton,
        noteButton,
        //documentsButton
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
    ) {
        items(buttonList) { button ->
            CategoryButton(
                TextTitle = button.TextTitle,
                objectCount = button.objectCount,
                Icon = button.Icon
            ) {
                if (button.TextTitle != "Прайс-лист" &&
                    button.TextTitle != "Запчасти" &&
                    button.TextTitle != "Документы"
                )
                    openSheet(scope = scope)
                button.Action()
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

data class Category(
    val TextTitle: String,
    val objectCount: String,
    val Icon: ImageVector,
    val Action:() -> Unit
)

@Composable
fun CategoryButton(
    TextTitle: String,
    Icon: ImageVector,
    objectCount: String,
    AddButtonClick: ()-> Unit
){
    TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = AddButtonClick,
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                backgroundColor = SecondColor
            )
        ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icon,
                modifier = Modifier
                    .padding(end = 15.dp, top = 15.dp)
                    .size(25.dp)
                    .align(Alignment.End),
                contentDescription = "",
                tint = ThirdColor.copy(0.8f)
            )
            Text(
                text = TextTitle,
                modifier = Modifier.padding(top = 15.dp, bottom = 30.dp),
                fontSize = 20.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500
            )
            Box(modifier = Modifier
                .size(40.dp)
                .padding(start = 15.dp, bottom = 15.dp)
                .clip(RoundedCornerShape(20))
                .background(FourthColor)
                .align(Alignment.Start)
            ){
                Text(
                    text = objectCount,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center),
                    letterSpacing = 0.sp,
                    color = ThirdColor,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
}



@Composable
fun WorkMenu() {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBottomSheet(scope = scope)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        SheetButton(scope = scope, AddButtonText.value, FirstButton, Icons.Outlined.Add)
        Spacer(modifier = Modifier.padding(top = 15.dp))
        SheetButton(scope = scope, ViewButtonText.value, SecondButton, Icons.Outlined.Visibility)
        Spacer(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun SheetButton(
    scope: CoroutineScope,
    ButtonText: String,
    ButtonClick: MutableState<() -> Unit>,
    Icon: ImageVector
){
    TextButton(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = {
            if(ButtonText != "Создать отчет")
                openSheet(scope = scope)
            ButtonClick.value()
        },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = SecondColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icon,
                contentDescription = "",
                modifier = Modifier.padding(start = 15.dp),
                tint = ThirdColor
            )
            Text(
                text = ButtonText,
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 16.sp,
                letterSpacing = 0.sp
            )
        }
    }
}