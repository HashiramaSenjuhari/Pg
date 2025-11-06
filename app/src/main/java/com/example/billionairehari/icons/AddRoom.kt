package com.example.billionairehari.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RoomIcon: ImageVector
    get() {
        if (_Meeting_room != null) return _Meeting_room!!

        _Meeting_room = ImageVector.Builder(
            name = "Meeting_room",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(120f, 840f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-640f)
                horizontalLineToRelative(400f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(600f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(80f)
                horizontalLineTo(680f)
                verticalLineToRelative(-600f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(600f)
                close()
                moveToRelative(160f, -640f)
                verticalLineToRelative(560f)
                close()
                moveToRelative(160f, 320f)
                quadToRelative(17f, 0f, 28.5f, -11.5f)
                reflectiveQuadTo(480f, 480f)
                reflectiveQuadToRelative(-11.5f, -28.5f)
                reflectiveQuadTo(440f, 440f)
                reflectiveQuadToRelative(-28.5f, 11.5f)
                reflectiveQuadTo(400f, 480f)
                reflectiveQuadToRelative(11.5f, 28.5f)
                reflectiveQuadTo(440f, 520f)
                moveTo(280f, 760f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(-560f)
                horizontalLineTo(280f)
                close()
            }
        }.build()

        return _Meeting_room!!
    }

private var _Meeting_room: ImageVector? = null

