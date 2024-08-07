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

public val IconPack.Starfilled: ImageVector
    get() {
        if (_starfilled != null) {
            return _starfilled!!
        }
        _starfilled = Builder(
            name = "Starfilled", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(12.7387f, 1.1928f)
                curveTo(12.6146f, 0.8944f, 12.3231f, 0.7f, 12.0f, 0.7f)
                curveTo(11.6769f, 0.7f, 11.3854f, 0.8944f, 11.2613f, 1.1928f)
                lineTo(8.6717f, 7.419f)
                lineTo(1.95f, 7.9579f)
                curveTo(1.6279f, 7.9837f, 1.3529f, 8.2008f, 1.2531f, 8.5081f)
                curveTo(1.1532f, 8.8154f, 1.248f, 9.1527f, 1.4935f, 9.3629f)
                lineTo(6.6147f, 13.7498f)
                lineTo(5.0501f, 20.3091f)
                curveTo(4.9751f, 20.6234f, 5.0966f, 20.952f, 5.358f, 21.1419f)
                curveTo(5.6195f, 21.3318f, 5.9695f, 21.3459f, 6.2453f, 21.1774f)
                lineTo(12.0f, 17.6624f)
                lineTo(17.7547f, 21.1774f)
                curveTo(18.0305f, 21.3459f, 18.3805f, 21.3318f, 18.642f, 21.1419f)
                curveTo(18.9034f, 20.952f, 19.0249f, 20.6234f, 18.9499f, 20.3091f)
                lineTo(17.3853f, 13.7498f)
                lineTo(22.5065f, 9.3629f)
                curveTo(22.7519f, 9.1527f, 22.8468f, 8.8154f, 22.7469f, 8.5081f)
                curveTo(22.6471f, 8.2008f, 22.3721f, 7.9837f, 22.05f, 7.9579f)
                lineTo(15.3283f, 7.419f)
                lineTo(12.7387f, 1.1928f)
                close()
            }
        }
            .build()
        return _starfilled!!
    }

private var _starfilled: ImageVector? = null
