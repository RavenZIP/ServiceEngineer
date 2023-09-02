package com.example.serviceengineer.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.serviceengineer.customui.DotsFillMaxWidth
import com.example.serviceengineer.screens.helpers.dataSequence
import com.example.serviceengineer.screens.helpers.dateCreate
import com.example.serviceengineer.screens.helpers.dateUpdate
import com.example.serviceengineer.screens.helpers.paperDescription
import com.example.serviceengineer.screens.helpers.paperImages
import com.example.serviceengineer.screens.helpers.paperName
import com.example.serviceengineer.screens.helpers.paperText
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.TitleGrey
import com.google.firebase.storage.StorageReference

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PaperCard(
    title: String,
    model: Any?,
    description: String,
    onClick: () -> Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)) {
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
            Column() {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        model = model,
                        contentDescription = "",
                        modifier = Modifier
                            .size(65.dp)
                    )
                    Text(
                        text = description,
                        color = TitleGrey,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BodyData(
    paperIconRef: StorageReference
){
    val dataList = dataSequence.value.split(';').toMutableStateList()
    var textIndex = 0
    var imageIndex = 1
    dataList.forEach {
        when (it) {
            "text" -> {
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        /*.padding(top = 20.dp, bottom = 20.dp)*/
                ) {
                    Text(
                        text = paperText[textIndex],
                        color = TitleGrey,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                textIndex += 1
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
            
            "image" -> {
                //Spacer(modifier = Modifier.padding(top = 20.dp))
                GlideImage(
                    model = paperIconRef.child(paperImages[imageIndex]),
                    contentDescription = ""
                )
                imageIndex += 1
            }
            
            "info" -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 10.dp)
                ) {
                    DotsFillMaxWidth()
                    Text(
                        text = "Дата написания",
                        fontSize = 14.sp,
                        color = TitleGrey,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.sp
                    )
                    Text(
                        text = dateCreate,
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
                        text = dateUpdate,
                        fontSize = 14.sp,
                        color = TitleGrey,
                        letterSpacing = 0.sp
                    )
                    DotsFillMaxWidth()
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageWithTitleCard(
    title: String,
    model: Any?,
    fontSize: Int,
    onClick: () -> Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15))
                        .background(ThirdColor.copy(0.8f))
                ) {*/
                GlideImage(
                    model = model,
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(0.dp), //было 10
                    //colorFilter = ColorFilter.tint(SecondColor)
                )
                //}
                Text(
                    text = title,
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeadData(paperIconRef: StorageReference){
    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = paperIconRef.child(paperImages[0]),
            contentDescription = "",
            modifier = Modifier.size(90.dp)
        )
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = paperName.value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = paperDescription.value,
                color = TitleGrey,
                fontSize = 14.sp,
                letterSpacing = 0.sp
            )
        }
    }
}