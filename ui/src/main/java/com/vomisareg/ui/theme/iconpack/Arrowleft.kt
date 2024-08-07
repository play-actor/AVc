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

public val IconPack.Arrowleft: ImageVector
    get() {
        if (_arrowleft != null) {
            return _arrowleft!!
        }
        _arrowleft = Builder(
            name = "Arrowleft", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(14.3546f, 18.7725f)
                curveTo(14.673f, 18.4662f, 14.6829f, 17.9597f, 14.3766f, 17.6413f)
                lineTo(8.91f, 11.998f)
                lineTo(14.3766f, 6.3546f)
                curveTo(14.6829f, 6.0362f, 14.673f, 5.5297f, 14.3546f, 5.2234f)
                curveTo(14.0362f, 4.9172f, 13.5297f, 4.927f, 13.2234f, 5.2454f)
                lineTo(7.2234f, 11.4434f)
                curveTo(6.9255f, 11.7531f, 6.9255f, 12.2428f, 7.2234f, 12.5526f)
                lineTo(13.2234f, 18.7505f)
                curveTo(13.5297f, 19.0689f, 14.0362f, 19.0788f, 14.3546f, 18.7725f)
                close()
            }
        }
            .build()
        return _arrowleft!!
    }

private var _arrowleft: ImageVector? = null
