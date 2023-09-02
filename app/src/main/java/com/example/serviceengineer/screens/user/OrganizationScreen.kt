package com.example.serviceengineer.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.main.Category
import com.example.serviceengineer.screens.main.CategoryButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var databaseCompany: DatabaseReference

@Composable
fun Organization(){
    TitleAppBar.value = CompanyName.value
    databaseCompany = FirebaseDatabase.getInstance().getReference("Organizations")

    val workerButton = Category(
        TextTitle = "Сотрудники",
        Icon = Icons.Outlined.Groups,
        objectCount = "0"
    ){

    }
    val clientsButton = Category(
        TextTitle = "Клиенты",
        Icon = Icons.Outlined.People,
        objectCount = "0"
    ) {

    }
    val statisticButton = Category(
        TextTitle = "Статистика",
        Icon = Icons.Outlined.ShowChart,
        objectCount = "0"
    ) {

    }

    val requestButton = Category(
        TextTitle = "Заявки",
        objectCount = "0",
        Icon = Icons.Outlined.Summarize
    ){

    }

    val reportButton = Category(
        TextTitle = "Отчеты",
        objectCount = "0",
        Icon = Icons.Outlined.FactCheck
    ){

    }

    val buttonList = mutableListOf(
        workerButton,
        clientsButton,
        requestButton,
        reportButton,
        statisticButton
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

            }
        }
    }
    /*LazyColumn (modifier = Modifier
            .padding(start = 20.dp),
    ) {
        items(CompanyRequestList) {
            Text(
                text = it.userEmail,
                modifier = Modifier.clickable {
                    val path = databaseCompany.ref.child(uJob.value).child("Request").child(it.id)
                    val data = mapOf<String, Any>(
                        "validation" to true
                    )
                    path.updateChildren(data)
                }
            )
        }
    }*/
}