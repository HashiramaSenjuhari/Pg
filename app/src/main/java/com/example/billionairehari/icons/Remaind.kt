package com.example.billionairehari.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RemaindIcon: ImageVector
    get() {
        if (_BellPlus != null) return _BellPlus!!

        _BellPlus = ImageVector.Builder(
            name = "BellPlus",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(19.3f, 14.8f)
                curveTo(20.1f, 16.4f, 21f, 17f, 21f, 17f)
                horizontalLineTo(3f)
                reflectiveCurveToRelative(3f, -2f, 3f, -9f)
                curveToRelative(0f, -3.3f, 2.7f, -6f, 6f, -6f)
                curveToRelative(1f, 0f, 1.9f, 0.2f, 2.8f, 0.7f)
                moveTo(10.3f, 21f)
                arcToRelative(1.94f, 1.94f, 0f, false, false, 3.4f, 0f)
                moveTo(15f, 8f)
                horizontalLineToRelative(6f)
                moveToRelative(-3f, -3f)
                verticalLineToRelative(6f)
            }
        }.build()

        return _BellPlus!!
    }

private var _BellPlus: ImageVector? = null

