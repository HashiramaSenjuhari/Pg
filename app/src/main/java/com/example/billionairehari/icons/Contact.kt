package com.example.billionairehari.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ContactIcon: ImageVector
    get() {
        if (_Contacts != null) return _Contacts!!

        _Contacts = ImageVector.Builder(
            name = "Contacts",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(160f, 920f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(80f)
                close()
                moveToRelative(0f, -800f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(80f)
                close()
                moveToRelative(320f, 400f)
                quadToRelative(50f, 0f, 85f, -35f)
                reflectiveQuadToRelative(35f, -85f)
                reflectiveQuadToRelative(-35f, -85f)
                reflectiveQuadToRelative(-85f, -35f)
                reflectiveQuadToRelative(-85f, 35f)
                reflectiveQuadToRelative(-35f, 85f)
                reflectiveQuadToRelative(35f, 85f)
                reflectiveQuadToRelative(85f, 35f)
                moveTo(160f, 800f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 720f)
                verticalLineToRelative(-480f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(160f, 160f)
                horizontalLineToRelative(640f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(880f, 240f)
                verticalLineToRelative(480f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 800f)
                close()
                moveToRelative(70f, -80f)
                quadToRelative(45f, -56f, 109f, -88f)
                reflectiveQuadToRelative(141f, -32f)
                reflectiveQuadToRelative(141f, 32f)
                reflectiveQuadToRelative(109f, 88f)
                horizontalLineToRelative(70f)
                verticalLineToRelative(-480f)
                horizontalLineTo(160f)
                verticalLineToRelative(480f)
                close()
                moveToRelative(118f, 0f)
                horizontalLineToRelative(264f)
                quadToRelative(-29f, -20f, -62.5f, -30f)
                reflectiveQuadTo(480f, 680f)
                reflectiveQuadToRelative(-69.5f, 10f)
                reflectiveQuadToRelative(-62.5f, 30f)
                moveToRelative(132f, -280f)
                quadToRelative(-17f, 0f, -28.5f, -11.5f)
                reflectiveQuadTo(440f, 400f)
                reflectiveQuadToRelative(11.5f, -28.5f)
                reflectiveQuadTo(480f, 360f)
                reflectiveQuadToRelative(28.5f, 11.5f)
                reflectiveQuadTo(520f, 400f)
                reflectiveQuadToRelative(-11.5f, 28.5f)
                reflectiveQuadTo(480f, 440f)
                moveToRelative(0f, 40f)
            }
        }.build()

        return _Contacts!!
    }

private var _Contacts: ImageVector? = null

