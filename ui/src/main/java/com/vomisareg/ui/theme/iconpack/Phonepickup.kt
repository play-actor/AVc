package com.vomisareg.ui.theme.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.vomisareg.ui.theme.IconPack

public val IconPack.Phonepickup: ImageVector
    get() {
        if (_phonepickup != null) {
            return _phonepickup!!
        }
        _phonepickup = Builder(
            name = "Phonepickup", defaultWidth = 24.0.dp, defaultHeight =
            24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd
                ) {
                    moveTo(19.9367f, 18.9318f)
                    curveTo(19.9383f, 18.9304f, 19.9402f, 18.9286f, 19.9425f, 18.9262f)
                    lineTo(21.9649f, 16.9039f)
                    curveTo(22.0524f, 16.8163f, 22.0402f, 16.671f, 21.9392f, 16.5994f)
                    lineTo(18.5135f, 14.1674f)
                    curveTo(18.434f, 14.1109f, 18.3253f, 14.12f, 18.2563f, 14.189f)
                    lineTo(16.9361f, 15.5092f)
                    curveTo(16.606f, 15.8393f, 16.0585f, 16.1062f, 15.444f, 15.9223f)
                    curveTo(14.6623f, 15.6885f, 13.2246f, 14.9883f, 11.1431f, 12.9068f)
                    curveTo(9.058f, 10.8217f, 8.3692f, 9.3929f, 8.1443f, 8.6151f)
                    curveTo(7.9681f, 8.0055f, 8.2369f, 7.4688f, 8.559f, 7.1467f)
                    lineTo(9.8862f, 5.8195f)
                    curveTo(9.9552f, 5.7505f, 9.9643f, 5.6417f, 9.9078f, 5.5622f)
                    lineTo(7.4637f, 2.1229f)
                    curveTo(7.392f, 2.022f, 7.2467f, 2.0098f, 7.1592f, 2.0974f)
                    lineTo(5.1354f, 4.1211f)
                    curveTo(5.1331f, 4.1233f, 5.1313f, 4.1252f, 5.1299f, 4.1267f)
                    curveTo(4.9602f, 4.4571f, 4.3565f, 5.7895f, 4.5018f, 7.7537f)
                    curveTo(4.6477f, 9.7244f, 5.5539f, 12.4226f, 8.5905f, 15.4592f)
                    curveTo(11.6256f, 18.4943f, 14.3256f, 19.4041f, 16.2997f, 19.5534f)
                    curveTo(18.2674f, 19.7022f, 19.6038f, 19.1016f, 19.9367f, 18.9318f)
                    close()
                    moveTo(16.1791f, 21.1488f)
                    curveTo(13.7842f, 20.9678f, 10.7356f, 19.867f, 7.4591f, 16.5906f)
                    curveTo(4.181f, 13.3125f, 3.0833f, 10.2649f, 2.9062f, 7.8717f)
                    curveTo(2.7304f, 5.4966f, 3.4676f, 3.8555f, 3.7164f, 3.3768f)
                    curveTo(3.801f, 3.2141f, 3.905f, 3.0887f, 4.004f, 2.9897f)
                    lineTo(6.0279f, 0.966f)
                    curveTo(6.8156f, 0.1782f, 8.1225f, 0.288f, 8.7679f, 1.1961f)
                    lineTo(11.212f, 4.6354f)
                    curveTo(11.7208f, 5.3513f, 11.6386f, 6.3298f, 11.0175f, 6.9509f)
                    lineTo(9.709f, 8.2594f)
                    curveTo(9.8757f, 8.7611f, 10.4336f, 9.9346f, 12.2744f, 11.7754f)
                    curveTo(14.1208f, 13.6218f, 15.3089f, 14.1905f, 15.8196f, 14.363f)
                    lineTo(17.1249f, 13.0576f)
                    curveTo(17.7457f, 12.4368f, 18.7238f, 12.3545f, 19.4397f, 12.8627f)
                    lineTo(22.8654f, 15.2947f)
                    curveTo(23.7741f, 15.9398f, 23.8842f, 17.2472f, 23.0962f, 18.0352f)
                    lineTo(21.0739f, 20.0576f)
                    curveTo(20.9737f, 20.1578f, 20.8467f, 20.2628f, 20.6822f, 20.3476f)
                    curveTo(20.2007f, 20.5961f, 18.5562f, 21.3285f, 16.1791f, 21.1488f)
                    close()
                }
            }
        }
            .build()
        return _phonepickup!!
    }

private var _phonepickup: ImageVector? = null
