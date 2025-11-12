package com.example.billionairehari.components.dashboard

import android.util.Log
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class RevenueBoardShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = drawBoard(size))
    }
}

private fun drawBoard(size: Size):Path{
    val scaleX = size.width / 399f
    val scaleY = 2.53f
    val height = 509f

    return Path().apply {
        moveTo(0f * scaleX, 24f * scaleY)
        cubicTo(
            0f * scaleX, 10.7452f * scaleY,
            10.7452f * scaleX, 0f * scaleY,
            24f * scaleX, 0f * scaleY
        )
        lineTo(169.194f * scaleX, 0f * scaleY)
        cubicTo(
            176.639f * scaleX, 0f * scaleY,
            183.662f * scaleX, 3.45473f * scaleY,
            188.206f * scaleX, 9.35188f * scaleY
        )
        lineTo(220.794f * scaleX, 51.6481f * scaleY)
        cubicTo(
            225.338f * scaleX, 57.5453f * scaleY,
            232.361f * scaleX, 61f * scaleY,
            239.806f * scaleX, 61f * scaleY
        )
        lineTo(375f * scaleX, 61f * scaleY)
        cubicTo(
            388.255f * scaleX, 61f * scaleY,
            399f * scaleX, 71.7452f * scaleY,
            399f * scaleX, 85f * scaleY
        )
        lineTo(399f * scaleX, 176f * height)
        cubicTo(
            399f * scaleX, 189.255f * height,
            388.255f * scaleX, 200f * height,
            375f * scaleX, 200f * height
        )
        lineTo(24f * scaleX, 200f * height)
        cubicTo(
            10.7452f * scaleX, 200f * scaleY,
            0f * scaleX, 189.255f * scaleY,
            0f * scaleX, 176f * scaleY
        )
        lineTo(0f * scaleX, 24f * height)
        close()
    }
}