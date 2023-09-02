package com.example.serviceengineer.screens.book

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.dataSequence
import com.example.serviceengineer.screens.helpers.dateCreate
import com.example.serviceengineer.screens.helpers.dateUpdate
import com.example.serviceengineer.screens.helpers.equipmentList
import com.example.serviceengineer.screens.helpers.paperDescription
import com.example.serviceengineer.screens.helpers.paperId
import com.example.serviceengineer.screens.helpers.paperImages
import com.example.serviceengineer.screens.helpers.paperName
import com.example.serviceengineer.screens.helpers.paperText
import com.example.serviceengineer.screens.helpers.storage
import com.example.serviceengineer.screens.helpers.url
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun Equipment(
    onEquipmentClick: () -> Unit
){
    TitleAppBar.value = "Оборудование"
    val paperIconRef = storage.child("icons/")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(equipmentList){
            PaperCard(
                title = it.name,
                model = paperIconRef.child(it.images[0]),
                description = it.description
            ) {
                paperId.value = it.id
                paperName.value = it.name
                paperDescription.value = it.description
                paperText = it.text
                paperImages = it.images.toMutableStateList()
                dataSequence.value = it.dataSequence
                url = it.url
                dateCreate = it.dateCreate
                dateUpdate = it.dateUpdate
                onEquipmentClick()
            }
        }
    }
    /*Button(onClick = {
        val pushKey = databaseEquipment.push().key.toString()
        val paper = Paper(
            paperId = pushKey,
            paperName = "Паяльная станция",
            paperDescription = "",
            paperText = "text",
            paperImages = listOf("1")
        )
            databaseEquipment.child(pushKey).setValue(paper)
    }) {
        Text(text = "tttt")
    }*/
}