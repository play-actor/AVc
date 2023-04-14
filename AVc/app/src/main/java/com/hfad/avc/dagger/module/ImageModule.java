package com.lastaurus.automatic_congratulations.dagger.module;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.lastaurus.automatic_congratulations.Util.CircularTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import dagger.Module;


@Module
public class ImageModule {
   public static void showImageForContact(Context context, ImageView imageView, String uri, int placeholder, boolean full) {
      if (!uri.equals("")) {
         RequestCreator requestCreator = new Picasso.Builder(context)
               .build()
               .load(uri)
               .placeholder(placeholder)
               .fit()
               .error(placeholder);
         if (!full) {
            requestCreator.centerInside().transform(new CircularTransformation());
         } else {
            requestCreator.centerCrop();
         }
         requestCreator.into(imageView);
      } else {
         imageView.setImageDrawable(ContextCompat.getDrawable(context, placeholder));
      }
   }

}
