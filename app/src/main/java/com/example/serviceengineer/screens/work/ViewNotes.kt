package com.example.serviceengineer.screens.work

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.customui.Style7
import com.example.serviceengineer.models.Note
import com.example.serviceengineer.screens.helpers.MultiButton
import com.example.serviceengineer.screens.helpers.NoteValues
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.mAuth
import com.example.serviceengineer.screens.helpers.noteList
import com.example.serviceengineer.screens.helpers.priceList
import com.example.serviceengineer.screens.helpers.selectedNotes
import com.example.serviceengineer.screens.helpers.selectedServices
import com.example.serviceengineer.ui.theme.ThirdColor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var databaseNotesList: DatabaseReference

val noteListLeft = mutableListOf<Note>()
val noteListRight = mutableListOf<Note>()

@Composable
fun ViewNotes(
    onNoteClick: () -> Unit
){
    databaseNotesList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Notes")
    TitleAppBar.value = "Заметки"
    noteListLeft.clear()
    noteListRight.clear()
    for (note in noteList.indices)
        if (note % 2 == 0)
            noteListLeft.add(noteList[note])
        else
            noteListRight.add(noteList[note])

    Row(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(Modifier.weight(1f)) {
            LazyColumn()
            {
                items(noteListLeft) { note ->
                    Style7(
                        note.title,
                        note.text,
                        if (selectedNotes.contains(note)) 2.dp else 1.dp,
                        onClick =  {
                            if (selectedNotes.isEmpty()) {
                                onNoteClick()
                                NoteValues = Note(note.id, note.title, note.text)
                            } else {
                                if (selectedNotes.contains(note))
                                    selectedNotes.remove(note)
                                else
                                    selectedNotes.add(note)
                            }
                        },
                        onLongPress = {
                            if (selectedNotes.isEmpty())
                                selectedNotes.add(note)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            LazyColumn()
            {
                items(noteListRight) { note ->
                    Style7(
                        note.title,
                        note.text,
                        if (selectedNotes.contains(note)) 2.dp else 1.dp,
                        onClick =  {
                            if (selectedNotes.isEmpty()) {
                                onNoteClick()
                                NoteValues = Note(note.id, note.title, note.text)
                            } else {
                                if (selectedNotes.contains(note))
                                    selectedNotes.remove(note)
                                else
                                    selectedNotes.add(note)
                            }
                        },
                        onLongPress = {
                            if (selectedNotes.isEmpty())
                                selectedNotes.add(note)
                        }
                    )
                }
            }
        }
    }
    if (selectedNotes.isNotEmpty())
        MultiButton(icon = Icons.Outlined.Delete, false) {
            for (item1 in noteList)
                for (item2 in selectedNotes)
                    if (item1 == item2) {
                        val serviceRef = databaseNotesList.ref.child(item1.id)
                        serviceRef.removeValue()
                    }
            selectedServices.clear()
        }
}