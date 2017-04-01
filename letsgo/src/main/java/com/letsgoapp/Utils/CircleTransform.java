package com.letsgoapp.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.squareup.picasso.Transformation;

/**
 * Created by normalteam on 07.02.17.
 */

public class CircleTransform implements Transformation {
    private final int BORDER_WIDTH =4;
    private int color;
    public CircleTransform(int color) {
        this.color = color;
    }
    public CircleTransform() {
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int radius = Math.min(h / 2, w / 2);
        Bitmap output = Bitmap.createBitmap(w + BORDER_WIDTH*2, h + BORDER_WIDTH*2, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        paint.setStyle(Paint.Style.FILL);

        c.drawCircle((w / 2) + BORDER_WIDTH, (h / 2) + BORDER_WIDTH, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        c.drawBitmap(bitmap, 4, 4, paint);
        paint.setXfermode(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(3);
        c.drawCircle((w / 2) + BORDER_WIDTH, (h / 2) + BORDER_WIDTH, radius, paint);

        bitmap.recycle();
        return output;
    }

    @Override
    public String key() {
        return "circle";
    }
}
