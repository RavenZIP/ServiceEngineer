package com.example.serviceengineer.screens.book

import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.serviceengineer.screens.helpers.MultiButton
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.cUser
import com.example.serviceengineer.screens.helpers.fileName
import com.example.serviceengineer.screens.helpers.schemeAuthorId
import com.example.serviceengineer.screens.helpers.schemeDescription
import com.example.serviceengineer.screens.helpers.schemeIcon
import com.example.serviceengineer.screens.helpers.schemeId
import com.example.serviceengineer.screens.helpers.schemeName
import com.example.serviceengineer.screens.helpers.schemeVerified
import com.example.serviceengineer.screens.helpers.snackbarHostState
import com.example.serviceengineer.screens.helpers.storage
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.TitleGrey
import com.example.serviceengineer.ui.theme.activeText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.io.File

private val editDescription = mutableStateOf(false)
private lateinit var databaseSchemes: DatabaseReference

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun ViewScheme() {
    TitleAppBar.value = "Схема"
    val scope = rememberCoroutineScope()
    val schemeImagesRef = storage.child("icons/")
    databaseSchemes = FirebaseDatabase.getInstance().getReference("Schemes")
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
/*            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(ThirdColor.copy(0.8f))
            ) {*/
                GlideImage(
                    model = schemeImagesRef.child(schemeIcon.value),
                    contentDescription = "",
                    modifier = Modifier
                        .size(90.dp)
                        .padding(0.dp),
                    //colorFilter = ColorFilter.tint(SecondColor)
                )
            //}
            Column(modifier = Modifier.padding(start = 20.dp)) {
                Text(
                    text = schemeName.value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.sp
                )
                Spacer(modifier = Modifier.padding(top = 5.dp))
                Text(
                    text = "Автор: " + if (cUser!!.uid == schemeAuthorId.value) "Вы" else "Сообщество",
                    color = TitleGrey,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(start = 5.dp)) {
            Text(
                text = "Статус схемы: " + if (!schemeVerified.value) "на проверке" else "подтверждена",
                color = TitleGrey,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
            if (!editDescription.value)
                Text(
                    text =
                    if (schemeDescription.value == "Добавьте описание" && cUser!!.uid != schemeAuthorId.value)
                        "Вы не можете редактировать описание схемы. Дождитесь, пока автор или администратор сделают это."
                    else if (schemeDescription.value == "Добавьте описание" && cUser!!.uid == schemeAuthorId.value)
                        schemeDescription.value + " нажав по этой надписи."
                    else
                        schemeDescription.value,
                    color = TitleGrey,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clip(RoundedCornerShape(15))
                        .clickable {
                            if (schemeDescription.value == "Добавьте описание" && cUser!!.uid == schemeAuthorId.value
                            ) {
                                editDescription.value = true
                                schemeDescription.value = ""
                            } else if (cUser!!.uid == schemeAuthorId.value) {
                                editDescription.value = true
                            }

                        }
                        .padding(5.dp)
                )
            else
                BasicTextField(
                    value = schemeDescription.value,
                    onValueChange = { schemeDescription.value = it },
                    textStyle = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                ) { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = schemeDescription.value,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        placeholder = {
                            Text(
                                text = "Текст",
                                fontSize = 16.sp,
                                color = activeText.copy(0.5f)
                            )
                        },
                        singleLine = true,
                        enabled = true,
                        interactionSource = MutableInteractionSource(),
                        contentPadding = PaddingValues(0.dp)
                    )
                }
        }
        Spacer(modifier = Modifier.padding(bottom = 90.dp))
    }

    MultiButton(icon = Icons.Outlined.FileDownload, false) {
        //val schemeImagesRef = storage.child("schemes/${schemeName.value}/${fileName.value}.pdf")
        /*schemeImagesRef.downloadUrl.addOnSuccessListener {
                Log.d("1", it.toString())
            *//*var download = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            var getPdf = DownloadManager.Request(it)
            getPdf.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            download.enqueue(getPdf)
            scope.launch {
                snackbarHostState.value.showSnackbar("Файл успешно получен")
            }*//*
                schemeImagesRef.getFile(it).addOnCompleteListener {

                    scope.launch {
                        snackbarHostState.value.showSnackbar("Файл успешно получен")
                    }
                }.addOnCanceledListener {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Ошибка загрузки файла!")
                    }
                }.addOnProgressListener {
                    Log.d("", "качаю")
                }
            }.addOnFailureListener {
            scope.launch {
                snackbarHostState.value.showSnackbar("Ошибка получения ссылки на файл. Возможно, что он отсутствует или плохое интернет-соединение")
            }
        }*/
        val storagePath = File(Environment.getExternalStorageDirectory(), "ServiceEngineer")
        // Create direcorty if not exists
        // Create direcorty if not exists
        if (!storagePath.exists()) {
            storagePath.mkdirs()
        }
        val myFile = File(storagePath, "${fileName.value}.pdf")
        val pdfFileRef = storage.child("schemes/${schemeName.value}/${fileName.value}" + ".pdf")
        //val localFile = File.createTempFile("files", "pdf")
        pdfFileRef.getFile(myFile).addOnSuccessListener {
            // Local temp file has been created
            scope.launch {
                snackbarHostState.value.showSnackbar("Файл успешно получен")
            }
        }.addOnFailureListener {
            // Handle any errors
            scope.launch {
                snackbarHostState.value.showSnackbar("Ошибка скачивания файла. Отсутствует файл или интернет-соединение")
            }
        }
    }
    if (editDescription.value)
        BackHandler(true) {
            scope.launch {
                if (schemeDescription.value == "")
                    schemeDescription.value = "Добавьте описание"
                else {
                    val path = databaseSchemes.ref.child(schemeId.value)
                    val data = mapOf(
                        "schemeDescription" to "Описание: " + schemeDescription.value,
                        "isVerified" to false
                    )
                    path.updateChildren(data)
                }
                editDescription.value = false
            }
        }
}
