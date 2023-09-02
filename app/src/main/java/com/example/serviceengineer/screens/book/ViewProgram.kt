package com.example.serviceengineer.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.serviceengineer.screens.helpers.MultiButton
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.paperDescription
import com.example.serviceengineer.screens.helpers.paperImages
import com.example.serviceengineer.screens.helpers.paperName
import com.example.serviceengineer.screens.helpers.paperText
import com.example.serviceengineer.screens.helpers.schemeAuthorId
import com.example.serviceengineer.screens.helpers.schemeName
import com.example.serviceengineer.screens.helpers.storage
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.TitleGrey

@Composable
fun ViewProgram(){
    TitleAppBar.value = "Описание ПО"
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
        HeadData(paperIconRef = paperIconRef)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        BodyData(paperIconRef = paperImagesRef)
        Spacer(modifier = Modifier.padding(top = 10.dp))
    }
/*    MultiButton(icon = Icons.Outlined.Language, false) {

    }*/
}