package com.example.serviceengineer.screens.book

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.customui.CustomBasicTextField
import com.example.serviceengineer.models.Instruction
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.mode
import com.example.serviceengineer.screens.helpers.uEmail
import com.example.serviceengineer.screens.helpers.uName
import com.example.serviceengineer.screens.helpers.uSurname
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var databaseInstructions: DatabaseReference

/*
@Composable
fun AddOrUpdateInstruction(
    onDoneClick: () -> Unit
) {
    TitleAppBar.value = "Инструкция"
    databaseInstructions =
        FirebaseDatabase.getInstance().getReference("Instructions")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp, end = 20.dp)
    ) {

        CustomBasicTextField(
            text = instructionTitle,
            fontSize = 20,
            placeholder = "Название"
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))

        CustomBasicTextField(
            text = instructionText,
            fontSize = 16,
            placeholder = "Текст"
        )
    }

    BackHandler(true) {
        if (instructionTitle.value != "" || instructionText.value != "") {
            if (mode.value == "add") {
                val instructionDate = SimpleDateFormat(
                    "dd MMMM yyyy, HH:mm", Locale.getDefault()
                )
                val pushKey = databaseInstructions.push().key.toString()
                val instruction = Instruction(
                    id = pushKey,
                    title = instructionTitle.value,
                    text = instructionText.value,
                    authorId = cUser!!.uid,
                    authorName = if (uSurname.value != "Нет данных" && uName.value != "Нет данных") uSurname.value + " " + uName.value else cUser.email!!,
                    dateCreate = instructionDate.format(Date()),
                    dateUpdate = instructionDate.format(Date())
                )
                databaseInstructions.child(pushKey).setValue(instruction)
            } else {
                val instructionDate = SimpleDateFormat(
                    "dd MMMM yyyy, HH:mm", Locale.getDefault()
                )
                val path = databaseInstructions.ref.child(instructionId.value)
                val data = mapOf(
                    "title" to instructionTitle.value,
                    "text" to instructionText.value,
                    "authorName" to instructionAuthorName.value ,
                    "dateUpdate" to instructionDate.format(Date())
                )
                path.updateChildren(data)
            }
            onDoneClick()
        }
        else
            onDoneClick()
    }
}*/
