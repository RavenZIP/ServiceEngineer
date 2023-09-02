package com.example.serviceengineer.screens.user

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serviceengineer.ui.theme.SecondColor
import com.example.serviceengineer.ui.theme.activeText

@Composable
fun IconWithText(
    Icon: ImageVector,
    TitleText: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(0.9f),
        onClick = { onClick() },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icon,
                contentDescription = ""
            )
            Text(
                text = TitleText,
                fontSize = 16.sp,
                color = activeText,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}