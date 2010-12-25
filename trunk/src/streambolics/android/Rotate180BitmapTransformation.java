package streambolics.android;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Rotate180BitmapTransformation extends MatrixBitmapTransformation
{

    @Override
    public Bitmap transform (Bitmap aSource)
    {
        Matrix m = getMatrix ();
        m.setRotate (180);
        m.postTranslate (aSource.getWidth (), aSource.getHeight ());
        return super.transform (aSource);
    }
}
