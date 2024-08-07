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

public val IconPack.Heartfilled: ImageVector
    get() {
        if (_heartfilled != null) {
            return _heartfilled!!
        }
        _heartfilled = Builder(
            name = "Heartfilled", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(2.9166f, 3.4817f)
                curveTo(5.2932f, 1.3493f, 8.9538f, 1.5413f, 11.0928f, 3.9107f)
                lineTo(12.0f, 4.9157f)
                lineTo(12.9073f, 3.9107f)
                curveTo(15.0462f, 1.5413f, 18.7068f, 1.3493f, 21.0835f, 3.4817f)
                curveTo(23.4601f, 5.6141f, 23.6527f, 9.2635f, 21.5138f, 11.6329f)
                lineTo(12.2582f, 21.8854f)
                curveTo(12.1202f, 22.0382f, 11.8798f, 22.0382f, 11.7418f, 21.8854f)
                lineTo(3.4862f, 11.6329f)
                curveTo(1.3473f, 9.2635f, 0.5399f, 5.6141f, 2.9166f, 3.4817f)
                close()
            }
        }
            .build()
        return _heartfilled!!
    }

private var _heartfilled: ImageVector? = null
