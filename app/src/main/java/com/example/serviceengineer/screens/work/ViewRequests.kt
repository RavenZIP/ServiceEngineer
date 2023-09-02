package com.example.serviceengineer.screens.work

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.models.Request
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.screens.main.*
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor

@Composable
fun ViewRequestList(
    viewRequest:() -> Unit
){
    TitleAppBar.value = "Заявки"
    Column(Modifier.fillMaxSize()) {
        HorizontalPagerRequestList(viewRequest)
    }
}

@Composable
fun ViewRequest(){
    TitleAppBar.value = "Заявка"
    onClickTopBarButton.value = {
        requestTemplate()
    }
    Column(Modifier.fillMaxSize()) {
        HorizontalPagerInfoWithTabs(count = 3)
    }
}

@Composable
fun RequestItem(
    request: Request,
    onClick:() -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(15))
            .clickable {
                requestId.value = request.id
                deviceType.value = request.deviceType
                deviceManufacturer.value = request.deviceManufacturer
                deviceModel.value = request.deviceModel
                deviceFault.value = request.deviceFault
                deviceKit.value = request.deviceKit
                deviceAppearance.value = request.deviceAppearance
                if (request.finished)
                    requestStatus.value = "Закрыта"
                else
                    requestStatus.value = "Открыта"
                requestDate.value = request.requestDate
                /*clientList.forEach {
                    if (it.clientPhone == request.clientPhone) {
                        clientSurname.value = it.clientSurname
                        clientName.value = it.clientName
                        clientPatronymic.value = it.clientPatronymic
                        clientPhone.value = it.clientPhone
                    }
                }*/
                onClick()
            },
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(15),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = request.deviceManufacturer + " " + request.deviceModel,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = ThirdColor.copy(0.8f)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "Тип устройства",
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = request.deviceType,
                fontSize = 14.sp,
                letterSpacing = 0.3.sp
            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = "Заявленная неисправность",
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = request.deviceFault,
                fontSize = 14.sp,
                letterSpacing = 0.3.sp
            )
        }
    }
}