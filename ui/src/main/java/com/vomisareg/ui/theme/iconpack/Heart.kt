package com.vomisareg.ui.theme.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.vomisareg.ui.theme.IconPack

public val IconPack.Heart: ImageVector
    get() {
        if (_heart != null) {
            return _heart!!
        }
        _heart = Builder(
            name = "Heart", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.6f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(11.4062f, 5.4517f)
                lineTo(12.0f, 6.1095f)
                lineTo(12.5938f, 5.4517f)
                lineTo(13.5011f, 4.4468f)
                curveTo(15.3444f, 2.4049f, 18.5005f, 2.239f, 20.5492f, 4.0771f)
                curveTo(22.5964f, 5.914f, 22.762f, 9.0563f, 20.9199f, 11.0968f)
                lineTo(12.0f, 20.9775f)
                lineTo(3.08f, 11.0968f)
                curveTo(1.238f, 9.0563f, 1.4036f, 5.914f, 3.4508f, 4.0771f)
                curveTo(5.4995f, 2.239f, 8.6556f, 2.4049f, 10.4989f, 4.4468f)
                lineTo(11.4062f, 5.4517f)
                close()
            }
        }
            .build()
        return _heart!!
    }

private var _heart: ImageVector? = null
