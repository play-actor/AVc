package com.vomisareg.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.vomisareg.ui.theme.iconpack.Arrowleft
import com.vomisareg.ui.theme.iconpack.Calendar
import com.vomisareg.ui.theme.iconpack.Heart
import com.vomisareg.ui.theme.iconpack.Heartfilled
import com.vomisareg.ui.theme.iconpack.Phonepickup
import com.vomisareg.ui.theme.iconpack.Settings
import com.vomisareg.ui.theme.iconpack.Star
import com.vomisareg.ui.theme.iconpack.Starfilled
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(
            Arrowleft, Calendar, Heart, Heartfilled, Phonepickup, Settings, Star,
            Starfilled
        )
        return __AllIcons!!
    }
