package com.lastaurus.automatic_congratulations.dagger.module

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.CircularTransformation
import com.squareup.picasso.Picasso
import dagger.Module
import javax.inject.Inject

@Module
class ImageModule @Inject constructor() {

   @Inject
   lateinit var context:Context

   fun showImageForContact(imageView: ImageView, uri: String, full: Boolean) {
      val placeholder: Int = if (full) R.drawable.no_foto else R.drawable.user_images
      if (uri != "") {
         val requestCreator = Picasso.Builder(context)
            .build()
            .load(uri)
            .placeholder(placeholder)
            .fit()
            .error(placeholder)
         if (!full) {
            requestCreator.centerInside().transform(CircularTransformation())
         } else {
            requestCreator.centerCrop()
         }
         requestCreator.into(imageView)
      } else {
         imageView.setImageDrawable(ContextCompat.getDrawable(context, placeholder))
      }
   }
}