package com.example.serviceengineer.screens.main

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.VideoCall
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.customui.CustomOutlinedTextFieldNumbers
import com.example.serviceengineer.customui.CustomOutlinedTextFieldSingleLine
import com.example.serviceengineer.models.Client
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.work.ProgressBar
import com.example.serviceengineer.screens.work.RequestButton
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.TitleGrey
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var databaseClients: DatabaseReference

@Composable
fun ClientButtons(){
    Row(modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(start = 0.dp).clickable {  }.padding(top = 20.dp, bottom = 20.dp).width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = "",
                tint = ThirdColor.copy(0.8f),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = "Вызов",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ThirdColor.copy(0.8f)
            )
        }
        Column(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp).clickable {  }.padding(top = 20.dp, bottom = 20.dp).width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Message,
                contentDescription = "",
                tint = ThirdColor.copy(0.8f),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = "Сообщение",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ThirdColor.copy(0.8f)
            )
        }
        Column(modifier = Modifier.padding(end = 10.dp).clickable {  }.padding(top = 20.dp, bottom = 20.dp).width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Videocam,
                contentDescription = "",
                tint = ThirdColor.copy(0.8f),
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = "Видеовызов",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ThirdColor.copy(0.8f)
            )
        }
    }
}

@Composable
fun ClientInfoCard(PhoneNumber: String, NumberRequest: String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 15.dp, end = 15.dp, top = 20.dp)
        .clip(RoundedCornerShape(12.dp)),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(5.dp),
        elevation = 0.dp) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Информация о клиенте",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        Icons.Outlined.Phone,
                        contentDescription = "",
                        tint = ThirdColor,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Text(
                        text = PhoneNumber,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Номер телефона",
                        fontSize = 13.sp,
                        color = TitleGrey
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        Icons.Outlined.Computer,
                        contentDescription = "",
                        tint = ThirdColor,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Text(
                        text = NumberRequest,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Обращений в сервис",
                        fontSize = 13.sp,
                        color = TitleGrey
                    )
                }
            }
        }
    }
}

@Composable
fun AddNewClientButton(
    onClick: () -> Unit
){
    TextButton(onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(0.95f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MainColor
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = Icons.Outlined.PersonAddAlt,
                contentDescription = "AddClient",
                tint = ThirdColor,
                modifier = Modifier.padding(start = 15.dp, end = 30.dp)
            )
            Text(
                text = "Добавить нового клиента",
                fontSize = 16.sp,
                letterSpacing = 0.sp,
                color = ThirdColor,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Composable
fun AddNewClient(
    exit:() -> Unit
){
    databaseClients = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid).child("Clients")
    TitleAppBar.value = "Клиент"
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MainColor)
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar()

            CustomOutlinedTextFieldSingleLine(
                value = clientSurname,
                text = "Фамилия",
                weight = 0.25f,
                id = 5,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientName,
                text = "Имя",
                weight = 0.25f,
                id = 6,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldSingleLine(
                value = clientPatronymic,
                text = "Отчество",
                weight = 0.25f,
                id = 7,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            CustomOutlinedTextFieldNumbers(
                value = clientPhone,
                text = "Номер телефона",
                weight = 0.25f,
                id = 8,
                enabled = true
            )
        }
        RequestButton(
            text = "Завершить",
            icon = Icons.Outlined.Done,
            false
        ) {
            if (progressValue.value == 1f) {
                val client = Client(
                    clientName = clientName.value.trim(),
                    clientSurname = clientSurname.value.trim(),
                    clientPatronymic = clientPatronymic.value.trim(),
                    clientPhone = clientPhone.value.trim()
                )
                databaseClients.push().setValue(client)
                exit()
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
    }
}

@Composable
fun ClientItem(TitleText: String, Symbol: Char, onClick: () -> Unit){
    TextButton(
        onClick = { onClick() },
        Modifier.fillMaxWidth(0.95f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier
                .size(50.dp)
                .padding(start = 0.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SecondColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Symbol.toString(),
                    fontSize = 28.sp,
                    color = ThirdColor,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = TitleText,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.3.sp,
                fontWeight = FontWeight.W400
            )
        }
    }
}