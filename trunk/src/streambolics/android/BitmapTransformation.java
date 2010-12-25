package streambolics.android;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class BitmapTransformation
{
    public abstract Bitmap transform (Bitmap aSource);

    public void untransformRect (Rect aRect)
    {
    }
}
