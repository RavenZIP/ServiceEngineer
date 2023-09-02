package com.example.serviceengineer.screens.book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.paperImages
import com.example.serviceengineer.screens.helpers.paperName
import com.example.serviceengineer.screens.helpers.storage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewInstruction(){
    TitleAppBar.value = "Инструкция"
    val paperIconRef = storage.child("icons/")
    val paperImagesRef = storage.child("images/")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = paperIconRef.child(paperImages[0]),
                contentDescription = "",
                modifier = Modifier.size(90.dp)
            )
            Text(
                text = paperName.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        BodyData(paperIconRef = paperImagesRef)
        Spacer(modifier = Modifier.padding(top = 10.dp))
    }
    /*Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = instructionTitle.value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "Автор инструкции",
            fontSize = 14.sp,
            color = TitleGrey,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
        Text(
            text = instructionAuthorName.value,
            fontSize = 14.sp,
            color = TitleGrey,
            letterSpacing = 0.sp
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Дата написания",
            fontSize = 14.sp,
            color = TitleGrey,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
        Text(
            text = instructionDateCreate.value,
            fontSize = 14.sp,
            color = TitleGrey,
            letterSpacing = 0.sp
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Дата последних изменений",
            fontSize = 14.sp,
            color = TitleGrey,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp
        )
        Text(
            text = instructionDateUpdate.value,
            fontSize = 14.sp,
            color = TitleGrey,
            letterSpacing = 0.sp
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = instructionText.value,
            fontSize = 14.sp,
            color = TitleGrey,
            letterSpacing = 0.sp
        )
    }*/
}