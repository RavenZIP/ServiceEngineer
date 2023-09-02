package com.example.serviceengineer.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.customui.Style1
import com.example.serviceengineer.screens.helpers.TitleAppBar
import com.example.serviceengineer.ui.theme.MainColor
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.ThirdColor

@Composable
fun Book(
    onFavoriteClick:() -> Unit,
    onParametersClick:() -> Unit,
    onSchemesClick:() -> Unit,
    onConstructionClick:() -> Unit,
    onProgramsClick:() -> Unit,
    onInstructionsClick:() -> Unit
){
    TitleAppBar.value = "Справочник"
    Column(modifier = Modifier
        .padding(bottom = 30.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Style1("Избранное",
            "Быстрый доступ к выбранному материалу",
            Icons.Rounded.FavoriteBorder,
            "Favorite",
            onClickAction = { onFavoriteClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Style1("Данные об устройствах",
            "Параметры для создания заявок, отчетов, прайс-листа или запчастей",
            Icons.Rounded.Devices,
            "Devices",
            onClickAction = { onParametersClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Style1("Схемы",
            "Схемы устройств и электронных компонентов, использующихся в устройствах",
            Icons.Rounded.Bolt,
            "Schemes",
            onClickAction = { onSchemesClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Style1("Оборудование для ремонта",
            "Подробное описание возможного оборудования, необходимого для ремонта устройств",
            Icons.Rounded.Construction,
            "Construction",
            onClickAction = { onConstructionClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Style1("Программное обеспечение",
            "Программы для тестирования корректной работоспособности устройств",
            Icons.Rounded.Terminal,
            "Programs",
            onClickAction = { onProgramsClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Style1("Инструкции",
            "Различные текстовые пояснения по обслуживанию устройств",
            Icons.Rounded.FormatListNumbered,
            "Instructions",
            onClickAction = { onInstructionsClick() },
            tint = ThirdColor.copy(0.8f),
            backgroundColor = SecondColor
        )
        Spacer(modifier = Modifier.padding(bottom = 65.dp))
    }
}