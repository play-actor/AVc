package com.lastaurus.automatic_congratulations

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

interface ChainHolder {
   val chain: ArrayList<WeakReference<Fragment>>
}