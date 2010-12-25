package streambolics.android;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Rotate90BitmapTransformation extends TransposeBitmapTransformation
{
    @Override
    public Bitmap transform (Bitmap aSource)
    {
        Matrix m = getMatrix ();
        m.setRotate (90);
        m.postTranslate (aSource.getHeight (), 0);
        return super.transform (aSource);
    }

}
