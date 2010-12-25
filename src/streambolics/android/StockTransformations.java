package streambolics.android;

public class StockTransformations
{
    private static BitmapTransformation _Rotate180;
    private static BitmapTransformation _Rotate270;
    private static BitmapTransformation _Rotate90;

    public static BitmapTransformation Rotate90 ()
    {
        if (_Rotate90 == null)
        {
            _Rotate90 = new Rotate90BitmapTransformation ();
        }
        return _Rotate90;
    }

    public static BitmapTransformation Rotate180 ()
    {
        if (_Rotate180 == null)
        {
            _Rotate180 = new Rotate180BitmapTransformation ();
        }
        return _Rotate180;
    }

    public static BitmapTransformation Rotate270 ()
    {
        if (_Rotate270 == null)
        {
            _Rotate270 = new Rotate270BitmapTransformation ();
        }
        return _Rotate270;
    }
}
