package com.lastaurus.automatic_congratulations.Util

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration : RecyclerView.ItemDecoration() {
   override fun getItemOffsets(
      outRect: Rect,
      view: View,
      parent: RecyclerView,
      state: RecyclerView.State,
   ) {

      val marginTop = 48
      val marginBottom = 88
      val spaceTop = TypedValue.applyDimension(
         TypedValue.COMPLEX_UNIT_DIP,
         marginTop.toFloat(),
         view.resources.displayMetrics
      ).toInt()
      val spaceBottom = TypedValue.applyDimension(
         TypedValue.COMPLEX_UNIT_DIP,
         marginBottom.toFloat(),
         view.resources.displayMetrics
      ).toInt()
      if (parent.getChildAdapterPosition(view) == 0) {
         outRect.top = spaceTop;
         outRect.bottom = 0;
      }
      if (parent.getChildLayoutPosition(view) == (parent.adapter?.itemCount?.minus(1))) {
         outRect.top = 0
         outRect.bottom = spaceBottom
      }
   }
}