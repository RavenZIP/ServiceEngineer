package com.example.serviceengineer.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.models.Client
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.google.firebase.database.*

private var Client = Client()
private lateinit var database: DatabaseReference

@Composable
fun Clients(
    addNewClient: () -> Unit,
    viewClientData: () -> Unit
){
    database = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Clients")
    TitleAppBar.value = "Клиенты"

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AddNewClientButton{
            addNewClient()
            progressValue.value = 0f
            keys.forEach { _ ->
                val target = keys.indexOf(1)
                if (target != -1)
                    keys[target] = 0
            }
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        LazyColumn {
            items(clientList) { client ->
                ClientItem(TitleText = client.clientSurname + " " + client.clientName + " " + client.clientPatronymic,
                    Symbol = client.clientSurname.first()
                ) {
                    Client = client
                    viewClientData()
                }
            }
        }
    }
}

@Composable
fun ViewClientData(){
    TitleAppBar.value = "О клиенте"
    var count = 0
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .padding(top = 40.dp)
            .size(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SecondColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = Client.clientSurname.first().toString(),
                fontSize = 40.sp,
                color = ThirdColor,
                fontWeight = FontWeight.Medium,
            )
        }
        Text(
            text = Client.clientSurname + " " + Client.clientName + " " + Client.clientPatronymic,
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
            textAlign = TextAlign.Center
        )
        /*requestList.forEach {
            if (it.clientPhone == Client.clientPhone)
                count += 1
        }*/
        Spacer(modifier = Modifier.padding(top = 20.dp))
        ClientButtons()
        ClientInfoCard(Client.clientPhone, count.toString())
    }
}