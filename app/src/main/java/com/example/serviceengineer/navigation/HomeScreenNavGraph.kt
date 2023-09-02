package com.example.serviceengineer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.serviceengineer.screens.book.*
import com.example.serviceengineer.screens.user.Organization
import com.example.serviceengineer.screens.main.UserProfile
import com.example.serviceengineer.screens.main.*
import com.example.serviceengineer.screens.user.AboutApp
import com.example.serviceengineer.screens.user.Help
import com.example.serviceengineer.screens.user.PayCard
import com.example.serviceengineer.screens.user.PayQiwi
import com.example.serviceengineer.screens.user.Settings
import com.example.serviceengineer.screens.user.Subscribe
import com.example.serviceengineer.screens.work.*

@Composable
fun HomeScreenNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomBarScreen.Home.route
    ){
        //Общая навигация по приложению
        composable(route = BottomBarScreen.Home.route) {
            Home()
        }
        composable(route = BottomBarScreen.Clients.route) {
            Clients(
                addNewClient = { navController.navigate(Client.AddNewClient.route) },
                viewClientData = { navController.navigate(Client.ViewClientData.route) }
            )
        }
        composable(route = BottomBarScreen.Book.route) {
            Book(
                onFavoriteClick = { navController.navigate(Book.Favorite.route) },
                onParametersClick = { navController.navigate(Book.ParameterCategories.route) },
                onSchemesClick = { navController.navigate(Book.Schemes.route) },
                onConstructionClick = { navController.navigate(Book.Construction.route) },
                onProgramsClick = { navController.navigate(Book.Programs.route) },
                onInstructionsClick = { navController.navigate(Book.Instructions.route) }
            )
        }
        composable(route = BottomBarScreen.Work.route) {
            Work(
                addRequest = { navController.navigate(Request.Create.route) },
                addReport = { navController.navigate(Report.Create.route) },
                addNote = { navController.navigate(Note.Create.route) },
                viewRequests = { navController.navigate(Request.ViewRequestList.route) },
                viewReports = { navController.navigate(Report.ViewReportList.route) },
                viewNotes = { navController.navigate(Note.View.route) },
                viewPriceList = { navController.navigate(PriceList.PriceListCategories.route) },
                viewSparePartsList = { navController.navigate(SparePartsList.SparePartsListCategories.route) }
            )
        }
        composable(route = BottomBarScreen.UserProfile.route) {
            UserProfile(
                subscribeButton = {
                    navController.navigate(Account.Subscribe.route)
                },
                organizationButton = {
                    navController.navigate(Account.Organization.route)
                },
                aboutAppButton = {
                    navController.navigate(Account.AboutApp.route)
                },
                helpButton = {
                    navController.navigate(Account.Help.route)
                },
                settingsButton = {
                    navController.navigate(Account.Settings.route)
                }
            )
        }

        //Навигация для экрана Клиенты
        //Добавление нового клиента
        composable(route = Client.AddNewClient.route) {
            AddNewClient(
                exit = { navController.popBackStack() }
            )
        }
        //Просмотр данных о клиенте
        composable(route = Client.ViewClientData.route) {
            ViewClientData()
        }

        //Навигация для экрана Справочник
        //Избранное
        composable(route = Book.Favorite.route) {
            FavoriteArticles()
        }
        //Данные об устройствах
        composable(route = Book.ParameterCategories.route) {
            ParameterCategories(
                viewParameter = { navController.navigate(Book.Parameter.route) }
            )
        }
        //Параметр
        composable(route = Book.Parameter.route) {
            Parameter()
        }
        //Схемы
        //Список схем
        composable(route = Book.Schemes.route) {
            Schemes(
                onViewSchemeClick = { navController.navigate(Book.ViewScheme.route) }
            )
        }
        //Просмотр схемы
        composable(route = Book.ViewScheme.route) {
            ViewScheme()
        }
        //Оборудование для ремонта
        //Список оборудования
        composable(route = Book.Construction.route) {
            Equipment(
                onEquipmentClick = { navController.navigate(Book.ViewEquipment.route) }
            )
        }
        //Просмотр оборудования
        composable(route = Book.ViewEquipment.route) {
            ViewEquipment()
        }
        //Программное обеспечение
        //Список программ
        composable(route = Book.Programs.route) {
            ProgramsList(
                onProgramsClick = { navController.navigate(Book.ViewProgram.route) }
            )
        }
        //Просмотр программы
        composable(route = Book.ViewProgram.route) {
            ViewProgram()
        }
        //Инструкции
        //Список инструкций
        composable(route = Book.Instructions.route) {
            Instructions(
                ViewInstruction = { navController.navigate(Book.ViewInstruction.route) },
                //AddOrUpdateInstruction = { navController.navigate(Book.AddOrUpdateInstruction.route) }
            )
        }
        //Просмотр инструкции
        composable(route = Book.ViewInstruction.route) {
            ViewInstruction()
        }
/*        //Добавление или редактирование инструкции
        composable(route = Book.AddOrUpdateInstruction.route) {
            AddOrUpdateInstruction(
                onDoneClick = { navController.popBackStack() }
            )
        }*/

        //Навигация для экрана Работа
        //Заявки
        composable(route = Request.Create.route){
            AddRequest(
                nextStepClick = {
                    navController.navigate(Request.CreateFinish.route)
                }
            )
        }
        composable(route = Request.CreateFinish.route){
            NextStep(
                saveAndExit = { navController.popBackStack("Work", inclusive = false, saveState = false) }
            )
        }
        composable(route = Request.ViewRequestList.route){
            ViewRequestList(
                viewRequest = { navController.navigate(Request.ViewRequest.route) }
            )
        }
        composable(route = Request.ViewRequest.route) {
            ViewRequest()
        }

        //Отчеты
        composable(route = Report.Create.route){
            AddReport(
                step2Click = { navController.navigate(Report.CreateReportStep2.route) },
                exit = { navController.popBackStack("Work", inclusive = false, saveState = false) }
            )
        }
        composable(route = Report.CreateReportStep2.route){
            AddReportStep2(
                step3Click = { navController.navigate(Report.CreateReportStep3.route) },
                exit = { navController.popBackStack("Work", inclusive = false, saveState = false) }
            )
        }
        composable(route = Report.CreateReportStep3.route){
            AddReportStep3(
                step4Click = { navController.navigate(Report.CreateReportStep4.route) },
                exit = { navController.popBackStack("Work", inclusive = false, saveState = false) }
            )
        }
        composable(route = Report.CreateReportStep4.route){
            AddReportStep4(
                saveAndExit = { navController.popBackStack("Work", inclusive = false, saveState = false) },
                exit = { navController.popBackStack("Work", inclusive = false, saveState = false) }
            )
        }
        composable(route = Report.ViewReportList.route){
            ViewReports(
                viewReport = { navController.navigate(Report.ViewReport.route) },
                updateReport = { navController.navigate(Report.Create.route) }
            )
        }
        composable(route = Report.ViewReport.route){
            ViewReport()
        }

        //Прайс-лист
        composable(route = PriceList.PriceListCategories.route) {
            PriceListCategories(onClick = {
                navController.navigate(PriceList.PriceListServices.route)
            })
        }
        composable(route = PriceList.PriceListServices.route) {
            PriceListServices()
        }

        //Запчасти
        composable(route = SparePartsList.SparePartsListCategories.route) {
            SparePartsCategories(onClick = {
                navController.navigate(SparePartsList.SparePartsListValues.route)
            })
        }
        composable(route = SparePartsList.SparePartsListValues.route) {
            SparePartsListValues()
        }

        //Заметки
        composable(route = Note.Create.route){
            AddNote(
                onDoneClick = { navController.popBackStack() }
            )
        }
        composable(route = Note.View.route){
            ViewNotes(
                onNoteClick = { navController.navigate(Note.Create.route) }
            )
        }

        //Навигация для экрана Профиль
        //Подписка
        composable(route = Account.Subscribe.route) {
            Subscribe(
                payCardClick = { navController.navigate(Account.PayCard.route) },
                payQIWIClick = { navController.navigate(Account.PayQIWI.route) }
            )
        }
        //Оплата картой
        composable(route = Account.PayCard.route) {
            PayCard()
        }
        //Оплата QIWI
        composable(route = Account.PayQIWI.route) {
            PayQiwi()
        }

        // Организация
        composable(route = Account.Organization.route) {
            Organization()
        }
        //О приложении
        composable(route = Account.AboutApp.route) {
            AboutApp()
        }
        //Помощь
        composable(route = Account.Help.route) {
            Help()
        }
        //Настройки
        composable(route = Account.Settings.route) {
            Settings()
        }
    }
}

sealed class Book(val route: String){
    object Favorite: Book(route = "Favorite")
    object ParameterCategories: Book(route = "ParameterCategories")
    object Parameter: Book(route = "Parameter")
    object Schemes: Book(route = "Schemes")
    object Construction: Book(route = "Construction")
    object Programs: Book(route = "Programs")
    object Instructions: Book(route = "Instructions")
    object ViewScheme: Book(route = "ViewScheme")
    object ViewProgram: Book(route = "ViewProgram")
    object ViewEquipment: Book(route = "ViewEquipment")
    object ViewInstruction: Book(route = "ViewInstruction")
    object AddOrUpdateInstruction: Book(route = "AddOrUpdateInstruction")
}

sealed class Client(val route: String){
    object AddNewClient: Client(route = "NewClient")
    object ViewClientData: Client(route = "ClientData")
}

sealed class Request(val route: String){
    object Create: Request(route = "CreateRequest")
    object CreateFinish: Request(route = "CreateRequestFinish")
    object ViewRequestList: Request(route = "ViewRequestList")
    object ViewRequest: Request(route = "ViewRequest")
}

sealed class PriceList(val route: String){
    object PriceListCategories: PriceList(route = "PriceListCategories")
    object PriceListServices: PriceList(route = "PriceListServices")
}

sealed class SparePartsList(val route: String){
    object SparePartsListCategories: SparePartsList(route = "SparePartsListCategories")
    object SparePartsListValues: SparePartsList(route = "SparePartsList")
}

sealed class Report(val route: String){
    object Create: Report(route = "CreateReport")
    object CreateReportStep2: Report(route = "CreateReportStep2")
    object CreateReportStep3: Report(route = "CreateReportStep3")
    object CreateReportStep4: Report(route = "CreateReportStep4")
    object ViewReportList: Report(route = "ViewReportList")
    object ViewReport: Report(route = "ViewReports")
}

sealed class Note(val route: String){
    object Create: Note(route = "CreateNote")
    object View: Note(route = "ViewNotes")
}

sealed class Account(val route: String){
    object Organization: Account(route = "Organization")
    object Subscribe: Account(route = "Subscribe")
    object Help: Account(route = "Help")
    object Settings: Account(route = "Settings")
    object AboutApp: Account(route = "AboutApp")
    object PayQIWI: Account(route = "PayQIWI")
    object PayCard: Account(route = "PayCard")
}
