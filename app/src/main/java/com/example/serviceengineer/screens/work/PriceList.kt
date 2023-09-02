package com.example.serviceengineer.screens.work

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.customui.CategoryButton
import com.example.serviceengineer.customui.ServiceCard
import com.example.serviceengineer.screens.helpers.*
import com.example.serviceengineer.ui.theme.FourthColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.example.serviceengineer.ui.theme.activeText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

private lateinit var databasePriceList: DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceListCategories(
    onClick: () -> Unit
){
    databasePriceList = FirebaseDatabase.getInstance().getReference("Users").child(
        mAuth.currentUser!!.uid).child("PriceList")
    TitleAppBar.value = "Прайс-лист"
    val categories = mutableListOf<String>()
    val scope = rememberCoroutineScope()

    parameterList.forEach {
        if (it.paramType == "Тип устройства")
            categories.add(it.paramName)
    }

    categories.add("Общее")
    categories.sort()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(15.dp)
    ) {
        items(categories) {
            CategoryButton(Text = it) {
                onClick()
                categoryName.value = it
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceListServices(){
    var boxPosition by remember { mutableStateOf(BoxPosition.Bottom) }
    val transition = updateTransition(targetState = boxPosition, label = "")
    val boxOffset by transition.animateOffset(label = "") { position ->
        when (position) {
            BoxPosition.Top -> Offset(0F, 0F)
            BoxPosition.Bottom -> Offset(0F, 60F)
        }
    }
    val priceCount = remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.padding(bottom = 15.dp, top = 15.dp)) {
        items(priceList) {
            if (it.category == categoryName.value)
                ServiceCard(
                    TitleText = it.service,
                    Text = it.description,
                    Price = it.price,
                    borderColor = if (selectedServices.contains(it)) ThirdColor else SecondColor
                ) {
                    if (!selectedServices.contains(it)) {
                        selectedServices.add(it)
                        priceCount.value += it.price.toInt()
                    }
                    else {
                        selectedServices.remove(it)
                        priceCount.value -= it.price.toInt()
                    }
                }
        }
    }

    Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
        MenuMultiButton(Icons.Outlined.Edit, 80.dp) {
            for (item1 in priceList)
                for (item2 in selectedServices)
                    if (item1 == item2) {
                        serviceKey.value = item1.id
                        serviceName.value = item1.service
                        serviceDescription.value = item1.description
                        servicePrice.value = item1.price
                        break
                    }
            modeModalLayout.value = "Редактирование услуги"
            mode.value = "Обновить"
            openSheet(scope = scope)
        }
        Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
            MenuMultiButton(icon = Icons.Outlined.Delete, padding = 140.dp) {
                for (item1 in priceList)
                    for (item2 in selectedServices)
                        if (item1 == item2) {
                            val serviceRef = databasePriceList.ref.child(item1.id)
                            serviceRef.removeValue()
                            selectedServices.clear()
                            break
                        }
            }
        }
    }

    if (priceCount.value != 0)
        PriceCountButton(priceCount = priceCount.value)

    if (selectedServices.isEmpty()) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Add, false) {
            mode.value = "Добавить"
            modeModalLayout.value = "Добавить услугу"
            openSheet(scope = scope)
        }
        priceCount.value = 0
    }
    else if (selectedServices.count() == 1) {
        MultiButton(icon = Icons.Outlined.DesignServices, false) {
            boxPosition = getNextPosition(boxPosition)
        }
    }
    else if (selectedServices.count() > 1) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Delete, false) {
            for (item1 in priceList)
                for (item2 in selectedServices)
                    if (item1 == item2) {
                        val serviceRef = databasePriceList.ref.child(item1.id)
                        serviceRef.removeValue()
                    }
            selectedServices.clear()
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
fun PriceCountButton(
    priceCount: Int
){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ){
        FloatingActionButton(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clip(RoundedCornerShape(16.dp)),
            onClick = {
            },
            shape = RectangleShape,
            backgroundColor = FourthColor
        ) {
            Text(
                text = "$priceCount ₽",
                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
                fontSize = 16.sp,
                color = activeText
            )
        }
    }
}