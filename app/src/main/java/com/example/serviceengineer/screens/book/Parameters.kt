package com.example.serviceengineer.screens.book

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.FourthColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

private lateinit var databaseParameters: DatabaseReference

@Composable
fun ParameterCategories(
    viewParameter:() -> Unit
){
    databaseParameters =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("Parameters")
    TitleAppBar.value = "Параметры"
    val categoriesList = mutableListOf(
        "Тип устройства",
        "Производитель",
        "Модель",
        "Неисправность"
    )
    val objectCounts = remember { mutableStateOf(0) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 30.dp)
    ) {
        items(categoriesList) {value ->
            objectCounts.value = 0
            parameterList.forEach {
                if (value == it.paramType)
                    objectCounts.value += 1
            }
            CategoryButton(
                TextTitle = value,
                objectCount = objectCounts.value.toString()
            ) {
                viewParameter()
                paramCategory.value = value
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Parameter(){
    TitleAppBar.value = paramCategory.value
    var boxPosition by remember { mutableStateOf(BoxPosition.Bottom) }
    val transition = updateTransition(targetState = boxPosition, label = "")
    val boxOffset by transition.animateOffset(label = "") { position ->
        when (position) {
            BoxPosition.Top -> Offset(0F, 0F)
            BoxPosition.Bottom -> Offset(0F, 60F)
        }
    }
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.padding(bottom = 15.dp, top = 15.dp)) {
        items(parameterList) {
            if (it.paramType == paramCategory.value)
                ParameterCard(
                    paramName = it.paramName,
                    borderColor = if (selectedParameters.contains(it)) ThirdColor else SecondColor
                ) {
                    if (!selectedParameters.contains(it)) {
                        selectedParameters.add(it)
                    }
                    else {
                        selectedParameters.remove(it)
                    }
                }
        }
    }

    Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
        MenuMultiButton(Icons.Outlined.Edit, 80.dp) {
            for (item1 in parameterList)
                for (item2 in selectedParameters)
                    if (item1 == item2) {
                        paramKey.value = item1.id
                        paramName.value = item1.paramName
                        break
                    }
            modeModalLayout.value = "Редактирование значение"
            mode.value = "Обновить"
            openSheet(scope = scope)
        }
        Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
            MenuMultiButton(icon = Icons.Outlined.Delete, padding = 140.dp) {
                for (item1 in parameterList)
                    for (item2 in selectedParameters)
                        if (item1 == item2) {
                            val paramRef = databaseParameters.ref.child(item1.id)
                            paramRef.removeValue()
                            selectedParameters.clear()
                            break
                        }
            }
        }
    }

    if (selectedParameters.isEmpty()) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Add, false) {
            mode.value = "Добавить"
            modeModalLayout.value = "Добавить значение"
            openSheet(scope = scope)
        }
    }
    else if (selectedParameters.count() == 1) {
        MultiButton(icon = Icons.Outlined.DesignServices, false) {
            boxPosition = getNextPosition(boxPosition)
        }
    }
    else if (selectedParameters.count() > 1) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Delete, false) {
            for (item1 in parameterList)
                for (item2 in selectedParameters)
                    if (item1 == item2) {
                        val paramRef = databaseParameters.ref.child(item1.id)
                        paramRef.removeValue()
                    }
            selectedParameters.clear()
        }
    }
    if (sheetState.isVisible)
        BackHandler(true) {
            scope.launch {
                sheetState.hide()
            }
        }
}

@Composable
fun ParameterCard(
    paramName: String,
    borderColor: Color,
    onClick:() -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        backgroundColor = SecondColor,
        shape = RoundedCornerShape(5.dp),
        elevation = 0.dp
    ) {
        Box(modifier = Modifier.padding(20.dp)){
            Text(
                text = paramName,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun CategoryButton(TextTitle: String, objectCount: String, AddButtonClick: ()-> Unit){
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = AddButtonClick,
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = SecondColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, end = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .size(40.dp)
                .padding(start = 15.dp, bottom = 15.dp)
                .clip(RoundedCornerShape(20))
                .background(FourthColor)
                .align(Alignment.End)
            ){
                Text(
                    text = objectCount,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.Center),
                    letterSpacing = 0.sp,
                    color = ThirdColor,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = TextTitle,
                modifier = Modifier.padding(bottom = 45.dp),
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500
            )
        }
    }
}