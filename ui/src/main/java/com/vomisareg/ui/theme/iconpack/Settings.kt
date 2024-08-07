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

public val IconPack.Settings: ImageVector
    get() {
        if (_settings != null) {
            return _settings!!
        }
        _settings = Builder(
            name = "Settings", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd
                ) {
                    moveTo(10.8345f, 0.2f)
                    curveTo(9.9646f, 0.2f, 9.2192f, 0.8221f, 9.0636f, 1.678f)
                    lineTo(8.9012f, 2.571f)
                    curveTo(8.8894f, 2.6362f, 8.8322f, 2.7227f, 8.7111f, 2.7658f)
                    curveTo(8.3985f, 2.8772f, 8.0932f, 3.0039f, 7.7962f, 3.1452f)
                    curveTo(7.6802f, 3.2003f, 7.5787f, 3.1796f, 7.5242f, 3.1419f)
                    lineTo(6.7775f, 2.6249f)
                    curveTo(6.0623f, 2.1298f, 5.0953f, 2.217f, 4.4801f, 2.8321f)
                    lineTo(2.832f, 4.4802f)
                    curveTo(2.2169f, 5.0954f, 2.1297f, 6.0624f, 2.6248f, 6.7776f)
                    lineTo(3.1418f, 7.5243f)
                    curveTo(3.1795f, 7.5789f, 3.2002f, 7.6803f, 3.1451f, 7.7963f)
                    curveTo(3.0039f, 8.0933f, 2.8771f, 8.3986f, 2.7658f, 8.7112f)
                    curveTo(2.7226f, 8.8323f, 2.6361f, 8.8894f, 2.5709f, 8.9013f)
                    lineTo(1.678f, 9.0636f)
                    curveTo(0.8221f, 9.2192f, 0.2f, 9.9647f, 0.2f, 10.8346f)
                    verticalLineTo(13.1654f)
                    curveTo(0.2f, 14.0354f, 0.8221f, 14.7808f, 1.678f, 14.9364f)
                    lineTo(2.5709f, 15.0988f)
                    curveTo(2.6361f, 15.1106f, 2.7226f, 15.1678f, 2.7657f, 15.2889f)
                    curveTo(2.8771f, 15.6015f, 3.0038f, 15.9067f, 3.1451f, 16.2037f)
                    curveTo(3.2002f, 16.3197f, 3.1795f, 16.4212f, 3.1418f, 16.4757f)
                    lineTo(2.6248f, 17.2224f)
                    curveTo(2.1296f, 17.9377f, 2.2168f, 18.9047f, 2.8319f, 19.5198f)
                    lineTo(4.4801f, 21.168f)
                    curveTo(5.0952f, 21.7831f, 6.0622f, 21.8703f, 6.7775f, 21.3751f)
                    lineTo(7.5241f, 20.8582f)
                    curveTo(7.5786f, 20.8204f, 7.6802f, 20.7997f, 7.7961f, 20.8549f)
                    curveTo(8.0931f, 20.9961f, 8.3984f, 21.1229f, 8.7111f, 21.2343f)
                    curveTo(8.8322f, 21.2774f, 8.8894f, 21.3639f, 8.9012f, 21.4291f)
                    lineTo(9.0636f, 22.322f)
                    curveTo(9.2192f, 23.1779f, 9.9646f, 23.8f, 10.8345f, 23.8f)
                    horizontalLineTo(13.1654f)
                    curveTo(14.0353f, 23.8f, 14.7807f, 23.1779f, 14.9364f, 22.322f)
                    lineTo(15.0987f, 21.4291f)
                    curveTo(15.1105f, 21.3639f, 15.1677f, 21.2775f, 15.2888f, 21.2343f)
                    curveTo(15.6015f, 21.123f, 15.9068f, 20.9962f, 16.2038f, 20.855f)
                    curveTo(16.3197f, 20.7998f, 16.4212f, 20.8205f, 16.4757f, 20.8583f)
                    lineTo(17.2223f, 21.3751f)
                    curveTo(17.9376f, 21.8703f, 18.9046f, 21.7831f, 19.5197f, 21.168f)
                    lineTo(21.1679f, 19.5198f)
                    curveTo(21.783f, 18.9047f, 21.8702f, 17.9377f, 21.375f, 17.2224f)
                    lineTo(20.8582f, 16.4759f)
                    curveTo(20.8204f, 16.4214f, 20.7997f, 16.3199f, 20.8549f, 16.2039f)
                    curveTo(20.9961f, 15.9069f, 21.1229f, 15.6015f, 21.2343f, 15.2889f)
                    curveTo(21.2774f, 15.1677f, 21.3639f, 15.1106f, 21.4291f, 15.0987f)
                    lineTo(22.3219f, 14.9364f)
                    curveTo(23.1778f, 14.7808f, 23.7999f, 14.0354f, 23.7999f, 13.1654f)
                    verticalLineTo(10.8346f)
                    curveTo(23.7999f, 9.9647f, 23.1778f, 9.2192f, 22.3219f, 9.0636f)
                    lineTo(21.4291f, 8.9013f)
                    curveTo(21.3639f, 8.8894f, 21.2774f, 8.8323f, 21.2343f, 8.7112f)
                    curveTo(21.1229f, 8.3985f, 20.9961f, 8.0932f, 20.8548f, 7.7961f)
                    curveTo(20.7997f, 7.6801f, 20.8204f, 7.5786f, 20.8581f, 7.5241f)
                    lineTo(21.375f, 6.7776f)
                    curveTo(21.8701f, 6.0624f, 21.7829f, 5.0953f, 21.1678f, 4.4802f)
                    lineTo(19.5196f, 2.8321f)
                    curveTo(18.9045f, 2.2169f, 17.9375f, 2.1298f, 17.2223f, 2.6249f)
                    lineTo(16.4757f, 3.1418f)
                    curveTo(16.4212f, 3.1795f, 16.3197f, 3.2002f, 16.2037f, 3.1451f)
                    curveTo(15.9067f, 3.0039f, 15.6014f, 2.8771f, 15.2888f, 2.7658f)
                    curveTo(15.1677f, 2.7226f, 15.1106f, 2.6361f, 15.0987f, 2.5709f)
                    lineTo(14.9364f, 1.678f)
                    curveTo(14.7807f, 0.8221f, 14.0353f, 0.2f, 13.1654f, 0.2f)
                    horizontalLineTo(10.8345f)
                    close()
                    moveTo(10.6378f, 1.9642f)
                    curveTo(10.6551f, 1.8691f, 10.7379f, 1.8f, 10.8345f, 1.8f)
                    horizontalLineTo(13.1654f)
                    curveTo(13.262f, 1.8f, 13.3449f, 1.8691f, 13.3622f, 1.9642f)
                    lineTo(13.5245f, 2.8572f)
                    curveTo(13.6497f, 3.5456f, 14.1515f, 4.0591f, 14.752f, 4.273f)
                    curveTo(15.0132f, 4.366f, 15.2683f, 4.472f, 15.5166f, 4.5901f)
                    curveTo(16.0927f, 4.864f, 16.811f, 4.8557f, 17.3864f, 4.4573f)
                    lineTo(18.133f, 3.9404f)
                    curveTo(18.2125f, 3.8854f, 18.3199f, 3.8951f, 18.3883f, 3.9634f)
                    lineTo(20.0364f, 5.6116f)
                    curveTo(20.1048f, 5.68f, 20.1145f, 5.7874f, 20.0595f, 5.8669f)
                    lineTo(19.5426f, 6.6134f)
                    curveTo(19.1442f, 7.1889f, 19.136f, 7.9071f, 19.4099f, 8.4832f)
                    curveTo(19.528f, 8.7316f, 19.634f, 8.9868f, 19.727f, 9.248f)
                    curveTo(19.9409f, 9.8485f, 20.4544f, 10.3503f, 21.1429f, 10.4755f)
                    lineTo(22.0357f, 10.6378f)
                    curveTo(22.1308f, 10.6551f, 22.2f, 10.7379f, 22.2f, 10.8346f)
                    verticalLineTo(13.1654f)
                    curveTo(22.2f, 13.2621f, 22.1308f, 13.3449f, 22.0357f, 13.3622f)
                    lineTo(21.1429f, 13.5246f)
                    curveTo(20.4544f, 13.6497f, 19.9409f, 14.1515f, 19.727f, 14.752f)
                    curveTo(19.634f, 15.0133f, 19.528f, 15.2685f, 19.4099f, 15.5168f)
                    curveTo(19.136f, 16.0929f, 19.1443f, 16.8112f, 19.5427f, 17.3866f)
                    lineTo(20.0595f, 18.1332f)
                    curveTo(20.1145f, 18.2126f, 20.1048f, 18.3201f, 20.0365f, 18.3884f)
                    lineTo(18.3883f, 20.0366f)
                    curveTo(18.32f, 20.1049f, 18.2125f, 20.1146f, 18.1331f, 20.0596f)
                    lineTo(17.3865f, 19.5428f)
                    curveTo(16.811f, 19.1443f, 16.0927f, 19.1361f, 15.5167f, 19.41f)
                    curveTo(15.2684f, 19.5281f, 15.0132f, 19.634f, 14.752f, 19.7271f)
                    curveTo(14.1515f, 19.9409f, 13.6497f, 20.4545f, 13.5245f, 21.1429f)
                    lineTo(13.3622f, 22.0358f)
                    curveTo(13.3449f, 22.1309f, 13.262f, 22.2f, 13.1654f, 22.2f)
                    horizontalLineTo(10.8345f)
                    curveTo(10.7379f, 22.2f, 10.6551f, 22.1309f, 10.6378f, 22.0358f)
                    lineTo(10.4754f, 21.1429f)
                    curveTo(10.3502f, 20.4544f, 9.8485f, 19.9409f, 9.248f, 19.727f)
                    curveTo(8.9867f, 19.634f, 8.7316f, 19.528f, 8.4832f, 19.4099f)
                    curveTo(7.9072f, 19.136f, 7.1889f, 19.1443f, 6.6134f, 19.5427f)
                    lineTo(5.8667f, 20.0596f)
                    curveTo(5.7873f, 20.1146f, 5.6798f, 20.1049f, 5.6115f, 20.0366f)
                    lineTo(3.9633f, 18.3884f)
                    curveTo(3.8949f, 18.3201f, 3.8853f, 18.2126f, 3.9403f, 18.1332f)
                    lineTo(4.4573f, 17.3864f)
                    curveTo(4.8557f, 16.811f, 4.8639f, 16.0927f, 4.59f, 15.5166f)
                    curveTo(4.472f, 15.2683f, 4.366f, 15.0132f, 4.273f, 14.752f)
                    curveTo(4.0591f, 14.1515f, 3.5456f, 13.6498f, 2.8571f, 13.5246f)
                    lineTo(1.9642f, 13.3622f)
                    curveTo(1.8691f, 13.3449f, 1.8f, 13.2621f, 1.8f, 13.1654f)
                    verticalLineTo(10.8346f)
                    curveTo(1.8f, 10.7379f, 1.8691f, 10.6551f, 1.9642f, 10.6378f)
                    lineTo(2.8572f, 10.4755f)
                    curveTo(3.5456f, 10.3503f, 4.0591f, 9.8485f, 4.273f, 9.248f)
                    curveTo(4.366f, 8.9868f, 4.472f, 8.7317f, 4.59f, 8.4834f)
                    curveTo(4.864f, 7.9073f, 4.8557f, 7.1891f, 4.4573f, 6.6136f)
                    lineTo(3.9403f, 5.8669f)
                    curveTo(3.8853f, 5.7874f, 3.895f, 5.68f, 3.9634f, 5.6116f)
                    lineTo(5.6115f, 3.9635f)
                    curveTo(5.6799f, 3.8951f, 5.7873f, 3.8854f, 5.8668f, 3.9404f)
                    lineTo(6.6134f, 4.4574f)
                    curveTo(7.1889f, 4.8558f, 7.9072f, 4.864f, 8.4833f, 4.5901f)
                    curveTo(8.7316f, 4.472f, 8.9867f, 4.3661f, 9.248f, 4.273f)
                    curveTo(9.8484f, 4.0592f, 10.3502f, 3.5456f, 10.4754f, 2.8572f)
                    lineTo(10.6378f, 1.9642f)
                    close()
                    moveTo(15.4f, 12.0f)
                    curveTo(15.4f, 13.8778f, 13.8777f, 15.4f, 12.0f, 15.4f)
                    curveTo(10.1222f, 15.4f, 8.5999f, 13.8778f, 8.5999f, 12.0f)
                    curveTo(8.5999f, 10.1222f, 10.1222f, 8.6f, 12.0f, 8.6f)
                    curveTo(13.8777f, 8.6f, 15.4f, 10.1222f, 15.4f, 12.0f)
                    close()
                    moveTo(17.0f, 12.0f)
                    curveTo(17.0f, 14.7614f, 14.7614f, 17.0f, 12.0f, 17.0f)
                    curveTo(9.2385f, 17.0f, 6.9999f, 14.7614f, 6.9999f, 12.0f)
                    curveTo(6.9999f, 9.2386f, 9.2385f, 7.0f, 12.0f, 7.0f)
                    curveTo(14.7614f, 7.0f, 17.0f, 9.2386f, 17.0f, 12.0f)
                    close()
                }
            }
        }
            .build()
        return _settings!!
    }

private var _settings: ImageVector? = null
