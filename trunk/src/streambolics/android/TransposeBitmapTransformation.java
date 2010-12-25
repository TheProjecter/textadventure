package streambolics.android;

import android.graphics.Rect;

public abstract class TransposeBitmapTransformation extends MatrixBitmapTransformation
{

    @Override
    public void untransformRect (Rect aRect)
    {
        aRect.set (0, 0, aRect.height (), aRect.width ());
    }
}
