package com.example.serviceengineer.screens.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.dataSequence
import com.example.serviceengineer.screens.helpers.dateCreate
import com.example.serviceengineer.screens.helpers.dateUpdate
import com.example.serviceengineer.screens.helpers.instructionsList
import com.example.serviceengineer.screens.helpers.paperDescription
import com.example.serviceengineer.screens.helpers.paperId
import com.example.serviceengineer.screens.helpers.paperImages
import com.example.serviceengineer.screens.helpers.paperName
import com.example.serviceengineer.screens.helpers.paperText
import com.example.serviceengineer.screens.helpers.storage
import com.example.serviceengineer.screens.helpers.url
import com.example.serviceengineer.ui.theme.SecondColor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var databaseInstructions: DatabaseReference

@Composable
fun Instructions(
    ViewInstruction: () -> Unit,
    //AddOrUpdateInstruction: () -> Unit
){
    TitleAppBar.value = "Инструкции"
    databaseInstructions =
        FirebaseDatabase.getInstance().getReference("Instructions")
    val paperIconRef = storage.child("icons/")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(instructionsList){
           /* InstructionCard(title = it.name){
                paperId.value = it.id
                paperName.value = it.name
                paperDescription.value = it.description
                paperText = it.text
                paperImages = it.images
                dataSequence.value = it.dataSequence
                url = it.url
                dateCreate = it.dateCreate
                dateUpdate = it.dateUpdate
                ViewInstruction()
                //mode.value = "update"
            }*/
            ImageWithTitleCard(
                title = it.name,
                model = paperIconRef.child(it.images[0]),
                fontSize = 16
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
                ViewInstruction()
            }
        }
    }
    /*if (uJob.value == "Администратор") {
        MultiButton(icon = Icons.Outlined.Add, false) {
            mode.value = "add"
            instructionTitle.value = ""
            instructionText.value = ""
            instructionId.value = ""
            instructionAuthorName.value = ""
            instructionAuthorId.value = ""
            instructionDateCreate.value = ""
            instructionDateUpdate.value = ""
            AddOrUpdateInstruction()
        }
    }*/
}

@Composable
private fun InstructionCard(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onClick()
                }
                .align(Alignment.Center),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = SecondColor,
            elevation = 0.dp
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}