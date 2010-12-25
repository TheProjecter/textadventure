package streambolics.android;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

public class MatrixBitmapTransformation extends BitmapTransformation
{
    private Matrix _Matrix = new Matrix ();
    private Rect _Rect;

    protected Bitmap transform (Bitmap aSource, Matrix aTrans)
    {
        return Bitmap.createBitmap (aSource, 0, 0, aSource.getWidth (), aSource.getHeight (), aTrans, true);
    }

    @Override
    public Bitmap transform (Bitmap aSource)
    {
        return transform (aSource, _Matrix);
    }

    protected Matrix getMatrix ()
    {
        return _Matrix;
    }
}
