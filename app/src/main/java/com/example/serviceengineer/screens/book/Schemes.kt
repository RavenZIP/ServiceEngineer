package com.example.serviceengineer.screens.book

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.serviceengineer.screens.helpers.MultiButton
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.registerAndLogin.FeaturesCard
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.serviceengineer.models.Scheme
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.fileName
import com.example.serviceengineer.screens.helpers.modeModalLayout
import com.example.serviceengineer.screens.helpers.openSheet
import com.example.serviceengineer.screens.helpers.progressValue
import com.example.serviceengineer.screens.helpers.schemeAuthorId
import com.example.serviceengineer.screens.helpers.schemeDescription
import com.example.serviceengineer.screens.helpers.schemeIcon
import com.example.serviceengineer.screens.helpers.schemeId
import com.example.serviceengineer.screens.helpers.schemeList
import com.example.serviceengineer.screens.helpers.schemeListIsVisible
import com.example.serviceengineer.screens.helpers.schemeName
import com.example.serviceengineer.screens.helpers.schemeVerified
import com.example.serviceengineer.screens.helpers.sheetState
import com.example.serviceengineer.screens.helpers.snackbarHostState
import com.example.serviceengineer.screens.helpers.storage
import com.example.serviceengineer.screens.helpers.uploadImage
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
private lateinit var databaseSchemes: DatabaseReference

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@SuppressLint("UnrememberedMutableState", "CheckResult")
@Composable
fun Schemes(
    onViewSchemeClick: () -> Unit
){

    TitleAppBar.value = "Схемы"
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            fileUri = uri
        }
    val scope = rememberCoroutineScope()

    if (schemeListIsVisible.value) {
        val schemeImagesRef = storage.child("icons/")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(schemeList) {
                ImageWithTitleCard(
                    title = it.schemeName,
                    model = schemeImagesRef.child(it.icon),
                    fontSize = 18
                ) {
                    schemeId.value = it.schemeId
                    fileName.value = it.fileName
                    schemeName.value = it.schemeName
                    schemeDescription.value = it.schemeDescription
                    schemeAuthorId.value  = it.authorId
                    schemeVerified.value = it.isVerified
                    schemeIcon.value = it.icon
                    onViewSchemeClick()
                }
                /*Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                schemeId.value = it.schemeId
                                fileName.value = it.fileName
                                schemeName.value = it.schemeName
                                schemeDescription.value = it.schemeDescription
                                schemeAuthorId.value  = it.authorId
                                schemeVerified.value = it.isVerified
                                onViewSchemeClick()
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
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(ThirdColor.copy(0.8f))
                            ) {
                                GlideImage(
                                    model = schemeImagesRef.child(it.schemeName).child("icon.png"),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(65.dp)
                                        .padding(10.dp),
                                    colorFilter = ColorFilter.tint(SecondColor)
                                )
                            }

                            Text(
                                text = it.schemeName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.sp,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                    }
                }*/
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeaturesCard(
                Icon = Icons.Outlined.FileUpload,
                TitleText = "Загрузка схемы",
                Text = "Для загрузки схемы нажмите на кнопку справа внизу и выберите pdf-файл на вашем устройстве",
                shapeRadius = 5
            )
            FeaturesCard(
                Icon = Icons.Outlined.Description,
                TitleText = "Добавьте информацию",
                Text = "После выбора файла заполните наименование схемы и наименование файла. " +
                        "Наименование файла должно строго соответствовать наименованию файла на вашем устройстве",
                shapeRadius = 5
            )
            FeaturesCard(
                Icon = Icons.Outlined.Feedback,
                TitleText = "Важно!",
                Text = "После загрузки файла он будет находиться на проверке около 24 часов и только потом станет " +
                        "доступным для остальных пользователей. Помимо этого у него появится иконка",
                shapeRadius = 5
            )
        }
    }

    MultiButton(icon = Icons.Outlined.FileUpload, false) {
        launcher.launch("application/pdf")
    }

    MultiButton(icon = Icons.Outlined.ViewInAr, true) {
        schemeListIsVisible.value = !schemeListIsVisible.value
    }

    if (fileUri != null){
        if (!sheetState.isVisible) {
            if (schemeName.value == "-") {
                fileUri = null
            }
            else {
                uploadImage.value = false
                modeModalLayout.value = "Загрузка схемы"
                openSheet(scope = scope)
            }
            schemeName.value = ""
            fileName.value = ""
        }
        if (sheetState.isVisible && uploadImage.value) {
            val schemeRef = storage.child("schemes/${schemeName.value}/${fileName.value}")
            databaseSchemes = FirebaseDatabase.getInstance().getReference("Schemes")
            schemeRef.putFile(fileUri!!).addOnCompleteListener{
                fileUri = null
                openSheet(scope = scope)
                scope.launch {
                    snackbarHostState.value.showSnackbar("Файл загружен")
                }
                val pushKey = databaseSchemes.push().key.toString()
                val scheme = Scheme(
                    schemeId = pushKey,
                    schemeName = schemeName.value,
                    fileName = fileName.value,
                    schemeDescription = "Добавьте описание",
                    authorId = cUser!!.uid,
                    icon = "",
                    isVerified = false
                )
                databaseSchemes.child(pushKey).setValue(scheme)
            }.addOnCanceledListener{
                openSheet(scope = scope)
                scope.launch {
                    snackbarHostState.value.showSnackbar("Ошибка загрузки файла!")
                }
            }.addOnProgressListener {
                progressValue.value = (1f * it.bytesTransferred) / it.totalByteCount
            }
        }
    }

    if (sheetState.isVisible)
        BackHandler(true) {
            scope.launch {
                sheetState.hide()
            }
        }
}