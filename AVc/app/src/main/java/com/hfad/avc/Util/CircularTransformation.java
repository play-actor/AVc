package com.lastaurus.automatic_congratulations.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class CircularTransformation implements Transformation {

   public CircularTransformation() {
   }

   @Override
   public Bitmap transform(final Bitmap source) {
      final Paint paint = new Paint();
      paint.setAntiAlias(true);
      paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

      final Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
      final Canvas canvas = new Canvas(output);
      int radius = source.getWidth() / 2;
      canvas.drawCircle(radius, source.getHeight() / 2, radius, paint);

      if (source != output)
         source.recycle();

      return output;
   }

   @Override
   public String key() {
      return "circle";
   }
}