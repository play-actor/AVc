package com.vomisareg.ui.theme.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.vomisareg.ui.theme.IconPack

public val IconPack.Calendar: ImageVector
    get() {
        if (_calendar != null) {
            return _calendar!!
        }
        _calendar = Builder(
            name = "Calendar", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(8.362f, 0.8f)
                curveTo(8.362f, 0.3582f, 8.0038f, 0.0f, 7.562f, 0.0f)
                curveTo(7.1201f, 0.0f, 6.762f, 0.3582f, 6.762f, 0.8f)
                verticalLineTo(2.0f)
                horizontalLineTo(5.0f)
                curveTo(3.3431f, 2.0f, 2.0f, 3.3431f, 2.0f, 5.0f)
                verticalLineTo(7.0087f)
                verticalLineTo(8.6087f)
                verticalLineTo(19.0f)
                curveTo(2.0f, 20.6569f, 3.3431f, 22.0f, 5.0f, 22.0f)
                horizontalLineTo(19.0f)
                curveTo(20.6569f, 22.0f, 22.0f, 20.6569f, 22.0f, 19.0f)
                verticalLineTo(5.0f)
                curveTo(22.0f, 3.3431f, 20.6569f, 2.0f, 19.0f, 2.0f)
                horizontalLineTo(17.8857f)
                verticalLineTo(0.8f)
                curveTo(17.8857f, 0.3582f, 17.5276f, 0.0f, 17.0857f, 0.0f)
                curveTo(16.6439f, 0.0f, 16.2857f, 0.3582f, 16.2857f, 0.8f)
                verticalLineTo(2.0f)
                horizontalLineTo(8.362f)
                verticalLineTo(0.8f)
                close()
                moveTo(3.6f, 8.6087f)
                verticalLineTo(19.0f)
                curveTo(3.6f, 19.7732f, 4.2268f, 20.4f, 5.0f, 20.4f)
                horizontalLineTo(19.0f)
                curveTo(19.7732f, 20.4f, 20.4f, 19.7732f, 20.4f, 19.0f)
                verticalLineTo(8.6087f)
                horizontalLineTo(3.6f)
                close()
                moveTo(20.4f, 7.0087f)
                verticalLineTo(5.0f)
                curveTo(20.4f, 4.2268f, 19.7732f, 3.6f, 19.0f, 3.6f)
                horizontalLineTo(17.8857f)
                verticalLineTo(3.9826f)
                curveTo(17.8857f, 4.4244f, 17.5276f, 4.7826f, 17.0857f, 4.7826f)
                curveTo(16.6439f, 4.7826f, 16.2857f, 4.4244f, 16.2857f, 3.9826f)
                verticalLineTo(3.6f)
                horizontalLineTo(8.362f)
                verticalLineTo(3.9826f)
                curveTo(8.362f, 4.4244f, 8.0038f, 4.7826f, 7.562f, 4.7826f)
                curveTo(7.1201f, 4.7826f, 6.762f, 4.4244f, 6.762f, 3.9826f)
                verticalLineTo(3.6f)
                horizontalLineTo(5.0f)
                curveTo(4.2268f, 3.6f, 3.6f, 4.2268f, 3.6f, 5.0f)
                verticalLineTo(7.0087f)
                horizontalLineTo(20.4f)
                close()
            }
        }
            .build()
        return _calendar!!
    }

private var _calendar: ImageVector? = null
