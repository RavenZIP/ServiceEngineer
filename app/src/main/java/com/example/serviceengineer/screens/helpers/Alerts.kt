package com.example.serviceengineer.screens.helpers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serviceengineer.ui.theme.SecondColor


@Composable
fun CustomSnackBar(
    snackbarHostState: MutableState<SnackbarHostState>
){
    SnackbarHost(
        hostState = snackbarHostState.value,
        snackbar = {snackbarData: SnackbarData ->
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = SecondColor,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 5.dp
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
                    Text(text = snackbarData.message, modifier = Modifier.padding(start = 10.dp))
                }
            }
        }
    )
}