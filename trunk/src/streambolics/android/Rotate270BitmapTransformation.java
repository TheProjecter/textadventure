package streambolics.android;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Rotate270BitmapTransformation extends TransposeBitmapTransformation
{
    @Override
    public Bitmap transform (Bitmap aSource)
    {
        Matrix m = getMatrix ();
        m.setRotate (270);
        m.postTranslate (0, aSource.getWidth ());
        return super.transform (aSource);
    }

}
