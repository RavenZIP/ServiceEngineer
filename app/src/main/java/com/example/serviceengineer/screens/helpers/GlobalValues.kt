package com.example.serviceengineer.screens.helpers

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.serviceengineer.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

//Основное
val mAuth = FirebaseAuth.getInstance()
val cUser = mAuth.currentUser
val storage = FirebaseStorage.getInstance().reference
val modeModalLayout = mutableStateOf("Выбор пользователя")
val FirstButton = mutableStateOf({ })
val SecondButton = mutableStateOf({ })
val progressValue = mutableStateOf(0f)
val keys = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("RememberReturnType")
val sheetState = ModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden,
    isSkipHalfExpanded = true
)
val snackbarHostState = mutableStateOf(SnackbarHostState())
val ServerVersion = mutableStateOf("")

//TopAppBar
val TitleAppBar = mutableStateOf("Главная")
val onClickTopBarButton = mutableStateOf({ })
val placeholderTopAppBar = mutableStateOf("")
var authIcon = mutableStateOf(Icons.Outlined.CloudOff)

//Пользователь
val uKey = mutableStateOf("")
val uEmail = mutableStateOf("")
val uSurname = mutableStateOf("")
val uName = mutableStateOf("")
val uPatronymic = mutableStateOf("")
val uJob = mutableStateOf("")
val uPost = mutableStateOf("")
val uType = mutableStateOf("")

//Компания
val CompanyId = mutableStateOf("")
val CompanyName = mutableStateOf("Нет данных")
val CompanyMembersCount = mutableStateOf("")
val CompanyList: MutableList<Company> = mutableListOf()
val CompanyRequestList: MutableList<RequestToCompany> = mutableListOf()

//Клиенты
val clientList: MutableList<Client> = mutableListOf()
val clientName = mutableStateOf("")
val clientSurname = mutableStateOf("")
val clientPatronymic = mutableStateOf("")
val clientPhone = mutableStateOf("")

//Справочник
val parameterList = mutableStateListOf<Parameter>()
val paramKey = mutableStateOf("")
val paramName = mutableStateOf("")
val paramCategory = mutableStateOf("")
val selectedParameters = mutableStateListOf<Parameter>()

//Заявки
val requestList: MutableList<Request> = mutableListOf()
val requestId = mutableStateOf("")
val deviceType = mutableStateOf("")
val deviceManufacturer = mutableStateOf("")
val deviceModel = mutableStateOf("")
val deviceFault = mutableStateOf("")
val deviceKit = mutableStateOf("")
val deviceAppearance = mutableStateOf("")
val requestDate = mutableStateOf("")
val requestStatus = mutableStateOf("")

//Отчеты
val localReportId = mutableStateOf(0)
val mode = mutableStateOf("add")
val reportsList: MutableList<Report> = mutableListOf()
val reportText = mutableStateOf("")
val deviceFaultFinal = mutableStateOf("")
var renderedServiceList = mutableListOf<String>()
var spentSparePartList = mutableListOf<String>()
var spentSparePartCount = mutableListOf<String>()
val sum = mutableStateOf("")
val reportDate = mutableStateOf("")

val renderedServicesStatus = mutableStateOf("")
val renderedServices = mutableStateOf("")
val sparePartsStatus = mutableStateOf("")
val spareParts = mutableStateOf("")

var loadService = false


//Заметки
val noteList = mutableStateListOf<Note>()
val NoteMode = mutableStateOf(0)
var NoteValues = Note()
val selectedNotes = mutableStateListOf<Note>()

//Прайс-лист
val categoryName = mutableStateOf("")
val serviceName = mutableStateOf("")
val serviceDescription = mutableStateOf("")
val servicePrice = mutableStateOf("")
val serviceKey = mutableStateOf("")
val priceList = mutableStateListOf<Service>()
val selectedServices = mutableStateListOf<Service>()

//Список запчастей
val sparePartName = mutableStateOf("")
val sparePartDescription = mutableStateOf("")
val sparePartPrice = mutableStateOf("")
val sparePartKey = mutableStateOf("")
val sparePartCountAvailable = mutableStateOf("")
val sparePartCountUsed = mutableStateOf("")
val sparePartCount = mutableStateOf("")
val sparePartsList = mutableStateListOf<SparePart>()
val selectedSpareParts = mutableStateListOf<SparePart>()

//Схемы
val schemeList = mutableStateListOf<Scheme>()
val fileName = mutableStateOf("")
val schemeName = mutableStateOf("")
val schemeAuthorId = mutableStateOf("")
val schemeDescription = mutableStateOf("")
val schemeId = mutableStateOf("")
val schemeVerified = mutableStateOf(false)
var schemeIcon = mutableStateOf("")
/* Лень было переименовывать, это статус загрузки пдф */
val uploadImage = mutableStateOf(false)
val schemeListIsVisible = mutableStateOf(false)

//Статьи
val paperId = mutableStateOf("")
val paperName = mutableStateOf("")
var paperText = listOf<String>()
val paperDescription = mutableStateOf("")
var paperImages = mutableStateListOf<String>()
val dataSequence = mutableStateOf("")
var url = ""
var dateCreate = ""
var dateUpdate = ""
val equipmentList = mutableStateListOf<Paper>()
val programsList = mutableStateListOf<Paper>()
val instructionsList = mutableStateListOf<Paper>()

fun clearData (){
    localReportId.value = 0

    deviceType.value = ""
    deviceManufacturer.value = ""
    deviceModel.value = ""
    deviceFault.value = ""
    deviceKit.value = ""
    deviceAppearance.value = ""

    clientName.value = ""
    clientSurname.value = ""
    clientPatronymic.value = ""
    clientPhone.value = ""

    reportText.value = ""

    deviceFaultFinal.value = ""

    renderedServiceList.clear()
    renderedServicesStatus.value = ""
    renderedServices.value = ""

    spentSparePartList.clear()
    sparePartsStatus.value = ""
    spareParts.value = ""

    sum.value = ""

    reportDate.value = ""
}

