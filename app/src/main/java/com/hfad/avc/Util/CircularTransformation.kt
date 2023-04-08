package com.hfad.avc.Util

import android.graphics.*
import com.squareup.picasso.Transformation

class CircularTransformation : Transformation {
   override fun transform(source: Bitmap): Bitmap {
      val paint = Paint()
      paint.isAntiAlias = true
      paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
      val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
      val canvas = Canvas(output)
      val radius = source.width / 2
      canvas.drawCircle(radius.toFloat(), (source.height / 2).toFloat(), radius.toFloat(), paint)
      if (source != output) source.recycle()
      return output
   }

   override fun key(): String {
      return "circle"
   }
}