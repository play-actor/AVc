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

public val IconPack.Star: ImageVector
    get() {
        if (_star != null) {
            return _star!!
        }
        _star = Builder(
            name = "Star", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(12.0f, 0.7f)
                curveTo(12.3231f, 0.7f, 12.6146f, 0.8944f, 12.7387f, 1.1928f)
                lineTo(15.3283f, 7.419f)
                lineTo(22.05f, 7.9579f)
                curveTo(22.3721f, 7.9837f, 22.6471f, 8.2008f, 22.7469f, 8.5081f)
                curveTo(22.8468f, 8.8154f, 22.7519f, 9.1527f, 22.5065f, 9.3629f)
                lineTo(17.3853f, 13.7498f)
                lineTo(18.9499f, 20.3091f)
                curveTo(19.0249f, 20.6234f, 18.9034f, 20.952f, 18.642f, 21.1419f)
                curveTo(18.3805f, 21.3318f, 18.0305f, 21.3459f, 17.7547f, 21.1774f)
                lineTo(12.0f, 17.6624f)
                lineTo(6.2453f, 21.1774f)
                curveTo(5.9695f, 21.3459f, 5.6195f, 21.3318f, 5.358f, 21.1419f)
                curveTo(5.0966f, 20.952f, 4.9751f, 20.6234f, 5.0501f, 20.3091f)
                lineTo(6.6147f, 13.7498f)
                lineTo(1.4935f, 9.3629f)
                curveTo(1.248f, 9.1527f, 1.1532f, 8.8154f, 1.2531f, 8.5081f)
                curveTo(1.3529f, 8.2008f, 1.6279f, 7.9837f, 1.95f, 7.9579f)
                lineTo(8.6717f, 7.419f)
                lineTo(11.2613f, 1.1928f)
                curveTo(11.3854f, 0.8944f, 11.6769f, 0.7f, 12.0f, 0.7f)
                close()
                moveTo(12.0f, 3.5832f)
                lineTo(9.9614f, 8.4846f)
                curveTo(9.8462f, 8.7616f, 9.5857f, 8.9509f, 9.2866f, 8.9748f)
                lineTo(3.9951f, 9.3991f)
                lineTo(8.0267f, 12.8526f)
                curveTo(8.2545f, 13.0477f, 8.354f, 13.3539f, 8.2844f, 13.6457f)
                lineTo(7.0527f, 18.8094f)
                lineTo(11.583f, 16.0423f)
                curveTo(11.839f, 15.8859f, 12.161f, 15.8859f, 12.417f, 16.0423f)
                lineTo(16.9473f, 18.8094f)
                lineTo(15.7156f, 13.6457f)
                curveTo(15.646f, 13.3539f, 15.7455f, 13.0477f, 15.9733f, 12.8526f)
                lineTo(20.0049f, 9.3991f)
                lineTo(14.7134f, 8.9748f)
                curveTo(14.4143f, 8.9509f, 14.1538f, 8.7616f, 14.0386f, 8.4846f)
                lineTo(12.0f, 3.5832f)
                close()
            }
        }
            .build()
        return _star!!
    }

private var _star: ImageVector? = null
