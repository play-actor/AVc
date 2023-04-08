package com.hfad.avc

import android.content.Context
import androidx.fragment.app.Fragment
import moxy.MvpAppCompatFragment
import java.lang.ref.WeakReference

abstract class BaseFragment : MvpAppCompatFragment() {
   override fun onAttach(context: Context) {
      super.onAttach(context)
      val activity = activity
      if (activity is ChainHolder) {
         (activity.chain).add(WeakReference<Fragment>(this))
      }
   }

   override fun onDetach() {
      val activity = activity
      if (activity is ChainHolder) {
         val chain: List<WeakReference<Fragment>> = (activity as ChainHolder).chain
         val it: MutableIterator<WeakReference<Fragment>> =
            chain.iterator() as MutableIterator<WeakReference<Fragment>>
         while (it.hasNext()) {
            val fragmentReference = it.next()
            val fragment = fragmentReference.get()
            if (fragment != null && fragment === this) {
               it.remove()
               break
            }
         }

      }
      super.onDetach()
   }
}