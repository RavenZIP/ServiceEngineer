package com.example.serviceengineer.screens.work

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.customui.CategoryButton
import com.example.serviceengineer.customui.SparePartCard
import com.example.serviceengineer.screens.helpers.BoxPosition
import com.example.serviceengineer.screens.helpers.MenuMultiButton
import com.example.serviceengineer.screens.helpers.MultiButton
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.screens.helpers.categoryName
import com.example.serviceengineer.screens.helpers.getNextPosition
import com.example.serviceengineer.screens.helpers.mAuth
import com.example.serviceengineer.screens.helpers.mode
import com.example.serviceengineer.screens.helpers.modeModalLayout
import com.example.serviceengineer.screens.helpers.openSheet
import com.example.serviceengineer.screens.helpers.parameterList
import com.example.serviceengineer.screens.helpers.selectedSpareParts
import com.example.serviceengineer.screens.helpers.sheetState
import com.example.serviceengineer.screens.helpers.sparePartCountAvailable
import com.example.serviceengineer.screens.helpers.sparePartDescription
import com.example.serviceengineer.screens.helpers.sparePartKey
import com.example.serviceengineer.screens.helpers.sparePartName
import com.example.serviceengineer.screens.helpers.sparePartPrice
import com.example.serviceengineer.screens.helpers.sparePartsList
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

private lateinit var databaseSparePartsList: DatabaseReference

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SparePartsCategories(
    onClick: () -> Unit
){
    databaseSparePartsList =
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser!!.uid)
            .child("SpareParts")
    TitleAppBar.value = "Запчасти"
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
fun SparePartsListValues() {
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
        items(sparePartsList) {
            if (it.category == categoryName.value)
                SparePartCard(
                    TitleText = it.sparePart,
                    Text = it.description,
                    Price = it.price,
                    Count = it.count,
                    borderColor = if (selectedSpareParts.contains(it)) ThirdColor else SecondColor
                ) {
                    if (!selectedSpareParts.contains(it)) {
                        selectedSpareParts.add(it)
                        priceCount.value += it.price.toInt()
                    }
                    else {
                        selectedSpareParts.remove(it)
                        priceCount.value -= it.price.toInt()
                    }
                }
        }
    }

    Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
        MenuMultiButton(Icons.Outlined.Edit, 80.dp) {
            for (item1 in sparePartsList)
                for (item2 in selectedSpareParts)
                    if (item1 == item2) {
                        sparePartKey.value = item1.id
                        sparePartName.value = item1.sparePart
                        sparePartDescription.value = item1.description
                        sparePartPrice.value = item1.price
                        sparePartCountAvailable.value = item1.count
                        break
                    }
            modeModalLayout.value = "Редактирование запчасти"
            mode.value = "Обновить"
            openSheet(scope = scope)
        }
        Box(modifier = Modifier.offset(boxOffset.x.dp, boxOffset.y.dp)) {
            MenuMultiButton(icon = Icons.Outlined.Delete, padding = 140.dp) {
                for (item1 in sparePartsList)
                    for (item2 in selectedSpareParts)
                        if (item1 == item2) {
                            val sparePartRef = databaseSparePartsList.ref.child(item1.id)
                            sparePartRef.removeValue()
                            selectedSpareParts.clear()
                            break
                        }
            }
        }
    }

    if (priceCount.value != 0)
        PriceCountButton(priceCount = priceCount.value)

    if (selectedSpareParts.isEmpty()) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Add, false) {
            mode.value = "Добавить"
            modeModalLayout.value = "Добавить запчасть"
            openSheet(scope = scope)
        }
        priceCount.value = 0
    }
    else if (selectedSpareParts.count() == 1) {
        MultiButton(icon = Icons.Outlined.DesignServices, false) {
            boxPosition = getNextPosition(boxPosition)
        }
    }
    else if (selectedSpareParts.count() > 1) {
        boxPosition = BoxPosition.Bottom
        MultiButton(icon = Icons.Outlined.Delete, false) {
            for (item1 in sparePartsList)
                for (item2 in selectedSpareParts)
                    if (item1 == item2) {
                        val sparePartRef = databaseSparePartsList.ref.child(item1.id)
                        sparePartRef.removeValue()
                    }
            selectedSpareParts.clear()
        }
    }

    if (sheetState.isVisible)
        BackHandler(true) {
            scope.launch {
                sheetState.hide()
            }
        }
}