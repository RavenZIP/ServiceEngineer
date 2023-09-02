package com.example.serviceengineer.ui.theme

import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val RoundedTop = AbsoluteRoundedCornerShape(topLeft = 20.dp, topRight = 20.dp)
val RoundedTop2 = AbsoluteRoundedCornerShape(topLeft = 40.dp, topRight = 40.dp)
val RoundedBottom = AbsoluteRoundedCornerShape(bottomLeft = 20.dp, bottomRight = 20.dp)
val RoundedBottom2 = AbsoluteRoundedCornerShape(bottomLeft = 40.dp, bottomRight = 40.dp)