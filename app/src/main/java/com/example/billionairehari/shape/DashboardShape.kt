package com.example.billionairehari.shape

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val rectangle1: ImageVector
    get() {
        if (_rectangle1 != null) return _rectangle1!!

        _rectangle1 = ImageVector.Builder(
            name = "rectangle1",
            defaultWidth = 399.dp,
            defaultHeight = 200.dp,
            viewportWidth = 399f,
            viewportHeight = 200f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFD9D9D9))
            ) {
                moveTo(0f, 24f)
                curveTo(0f, 10.7452f, 10.7452f, 0f, 24f, 0f)
                lineTo(179.194f, 0f)
                curveTo(186.639f, 0f, 193.662f, 3.45473f, 198.206f, 9.35188f)
                lineTo(230.794f, 51.6481f)
                curveTo(235.338f, 57.5453f, 242.361f, 61f, 249.806f, 61f)
                lineTo(375f, 61f)
                curveTo(388.255f, 61f, 399f, 71.7452f, 399f, 85f)
                verticalLineTo(176f)
                curveTo(399f, 189.255f, 388.255f, 200f, 375f, 200f)
                lineTo(24f, 200f)
                curveTo(10.7452f, 200f, 0f, 189.255f, 0f, 176f)
                lineTo(0f, 24f)
                close()
            }
        }.build()
        
        return _rectangle1!!
    }

private var _rectangle1: ImageVector? = null

