package com.example.serviceengineer.screens.work

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.customui.CustomBasicTextField
import com.example.serviceengineer.models.Note
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.activeText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var database: DatabaseReference

@Composable
fun AddNote(
    onDoneClick: () -> Unit
){
    if (NoteMode.value == 0){
        NoteValues.title = ""
        NoteValues.text = ""
    }
    val title = remember { mutableStateOf(NoteValues.title) }
    val text = remember { mutableStateOf(NoteValues.text) }
    database = FirebaseDatabase.getInstance().getReference("Users").child(
        mAuth.currentUser!!.uid).child("Notes")

    TitleAppBar.value = "Заметка"

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 15.dp, end = 20.dp)) {

        CustomBasicTextField(
            text = title,
            fontSize = 20,
            placeholder = "Название"
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))

        CustomBasicTextField(
            text = text,
            fontSize = 16,
            placeholder = "Текст"
        )
    }
    BackHandler(true) {
        if (title.value != "" || text.value != "") {
            if (NoteMode.value == 0) {
                val pushKey = database.push().key.toString()
                val note = Note(pushKey, title.value, text.value)
                database.child(pushKey).setValue(note)
            } else {
                val path = database.ref.child(NoteValues.id)
                val data = mapOf(
                    "title" to title.value,
                    "text" to text.value
                )
                path.updateChildren(data)
                NoteValues = Note()
            }
            onDoneClick()
        }
        else
            onDoneClick()
    }
}